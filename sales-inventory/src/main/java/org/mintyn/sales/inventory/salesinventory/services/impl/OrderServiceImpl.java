package org.mintyn.sales.inventory.salesinventory.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mintyn.inventory.response.exception.ApiBadRequestException;
import org.mintyn.inventory.response.exception.ApiResourceNotFoundException;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;
import org.mintyn.sales.inventory.salesinventory.enums.OrderStatus;
import org.mintyn.sales.inventory.salesinventory.repositories.OrderItemRepository;
import org.mintyn.sales.inventory.salesinventory.entities.Order;
import org.mintyn.sales.inventory.salesinventory.entities.OrderItem;
import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.mintyn.sales.inventory.salesinventory.repositories.OrderRepository;
import org.mintyn.sales.inventory.salesinventory.repositories.ProductRepository;
import org.mintyn.sales.inventory.salesinventory.services.OrderService;
import org.mintyn.sales.inventory.salesinventory.utils.MapstructMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final KafkaTemplate<String, List<OrderResponse>> kafkaTemplate;
    private final MapstructMapper mapper;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public OrderItemResponse orderProduct(final long productId, final OrderRequest request) {
        int quantityOrdered = request.getQuantity();
        if (quantityOrdered <= 0) {
            throw new ApiBadRequestException("Product quantity ordered can not be less than 1");
        }

        if (StringUtils.isBlank(request.getCustomerName())) {
            throw new ApiBadRequestException("Customer Name cannot be blank");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiResourceNotFoundException("Product does not exist"));

        int productQuantity = product.getProductQuantity();

        if (productQuantity < quantityOrdered) {
            throw new ApiBadRequestException("Product out of stock");
        }

        BigDecimal totalCost = product.getPrice()
                .multiply(BigDecimal.valueOf(quantityOrdered));

        int quantityRemaining = productQuantity - quantityOrdered;
        product.setProductQuantity(quantityRemaining);

        List<Order> orders = orderRepository.findAll();
        if (orders.isEmpty()) {
            Order order = Order.builder()
                    .orderId(RandomStringUtils.randomAlphanumeric(12))
                    .customerPhoneNumber(request.getCustomerPhoneNumber())
                    .customerName(request.getCustomerName())
                    .createdAt(LocalDate.now())
                    .build();
            orders = new ArrayList<>();
            orders.add(order);
        }

        Order order = orders.get(0);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDate.now())
                .orderQuantity(quantityOrdered)
                .productPrice(product.getPrice())
                .totalProductPrice(totalCost)
                .build();

        List<OrderItem> items = order.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(orderItem);
        order.setItems(items);
        orderRepository.save(order);

        return response(orderItem);
    }

    private OrderItemResponse response(final OrderItem orderItem) {
        OrderItemResponse response = mapper.createResponseFromOrderItem(orderItem);
        response.setProductName(orderItem.getProduct().getProductName());
        return response;
    }

    @Override
    public OrderItemResponse removeOrderedIem(final long orderItemId) {
        TypedQuery<OrderItem> query = entityManager.createQuery("SELECT o FROM OrderItem o " +
                "where o.id = :orderId and o.status = :status", OrderItem.class);

        OrderItem item = query.setParameter("orderId", orderItemId)
                .setParameter("status", OrderStatus.PENDING)
                .getSingleResult();

        if (item == null) {
            throw new ApiResourceNotFoundException("Product not found");
        }

        Product product = item.getProduct();
        product.setProductQuantity(product.getProductQuantity() + item.getOrderQuantity());
        Order order = item.getOrder();
        List<OrderItem> items = order.getItems();
        items.remove(item);
        order.setItems(items);
        orderRepository.save(order);
        productRepository.save(product);

        return null;
    }

    @Override
    public  void checkoutOrder(final long orderId) {
        TypedQuery<OrderItem> query = entityManager.createQuery("SELECT o FROM OrderItem o " +
                "where o.order.id = :orderId and o.status = :status", OrderItem.class);

        List<OrderItem> items = query.setParameter("orderId", orderId)
                .setParameter("status", OrderStatus.PENDING)
                .getResultList();

        if (items.isEmpty()) {
            throw new ApiResourceNotFoundException("You have not made an order");
        }
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ApiResourceNotFoundException("You have not made an order"));

        List<OrderResponse> responses = items.stream()
                        .map(orderItem -> OrderResponse.builder()
                                .orderId(order.getOrderId())
                                .customerName(order.getCustomerName())
                                .customerPhoneNumber(order.getCustomerPhoneNumber())
                                .productId(orderItem.getProduct().getId())
                                .productName(orderItem.getProduct().getProductName())
                                .quantity(orderItem.getOrderQuantity())
                                .price(orderItem.getProductPrice())
                                .totalPrice(orderItem.getTotalProductPrice())
                                .orderCreatedDate(orderItem.getCreatedAt())
                                .build()).collect(Collectors.toList());

        OrderResponse response = new OrderResponse();
        response.setOrderId(String.valueOf(orderId));

        sendOrderReport(responses);
//        kafkaTemplate.send("orderReport", responses);
        List<OrderItem> updated = items.stream()
                .peek(orderItem -> {
                    orderItem.setStatus(OrderStatus.COMPLETE);
                    orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toList());
        order.setItems(updated);
        orderRepository.save(order);
    }

    @Override
    public List<OrderItemResponse> orderItemResponse(final long orderId) {
        TypedQuery<OrderItemResponse> query = entityManager.createQuery("SELECT new org.mintyn.sales.inventory.salesinventory.dto" +
                ".responsedto.OrderItemResponse" +
                        "(o.id, o.product.productName, o.productPrice, o.totalProductPrice, o.orderQuantity, o.createdAt," +
                        " o.status) from OrderItem o where o.status=:status", OrderItemResponse.class);
        query.setParameter("status", OrderStatus.PENDING);
        return query.getResultList();
    }

    private void sendOrderReport(List<OrderResponse> orderResponseList) {

        CompletableFuture<SendResult<String, List<OrderResponse>>> completableFuture =
                kafkaTemplate.send("orderReport", orderResponseList);

        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                throw new ApiBadRequestException("Incomplete order");
            } else {

                RecordMetadata metadata = result.getRecordMetadata();
                log.info("topic: {}, partition: {}, offset: {}, timestamp: {}, key: {}, value: {}",
                        metadata.topic(), metadata.partition(), metadata.hasOffset(),
                        metadata.hasTimestamp(), metadata.serializedKeySize(), metadata.serializedValueSize());
            }
        });
    }
}
