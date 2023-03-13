package org.mintyn.sales.inventory.salesinventory.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mintyn.inventory.response.exception.ApiBadRequestException;
import org.mintyn.inventory.response.exception.ApiResourceNotFoundException;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;
import org.mintyn.sales.inventory.salesinventory.entities.OrderItem;
import org.mintyn.sales.inventory.salesinventory.entities.Product;
import org.mintyn.sales.inventory.salesinventory.enums.OrderStatus;
import org.mintyn.sales.inventory.salesinventory.kafka.orderreports.OrderReportSenderService;
import org.mintyn.sales.inventory.salesinventory.repositories.OrderItemRepository;
import org.mintyn.sales.inventory.salesinventory.repositories.OrderRepository;
import org.mintyn.sales.inventory.salesinventory.repositories.ProductRepository;
import org.mintyn.sales.inventory.salesinventory.services.OrderService;
import org.mintyn.sales.inventory.salesinventory.services.impl.OrderServiceImpl;
import org.mintyn.sales.inventory.salesinventory.utils.MapstructMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderReportSenderService reportSenderService;

    @Mock
    private MapstructMapper mapper;

    private OrderRequest orderRequest;

    private Product product;

    @BeforeEach
    void init() {
        orderService = new OrderServiceImpl(productRepository,
                orderRepository, orderItemRepository, reportSenderService, mapper);
        orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Oma");
        orderRequest.setQuantity(3);
        orderRequest.setCustomerPhoneNumber("111-222-333");

        product = new Product();
        product.setProductName("Oma");
        product.setProductQuantity(3);
        product.setPrice(BigDecimal.valueOf(5));
        product.setDescription("Description");
        product.setId(1L);
    }

    @Test
    void testOrderProduct() {
        OrderItemResponse orderItemResponse = new OrderItemResponse();
        orderItemResponse.setProductName("Oma");
        orderItemResponse.setOrderQuantity(3);
        orderItemResponse.setProductPrice(BigDecimal.valueOf(5));

        orderItemResponse.setStatus(OrderStatus.PENDING);
        orderItemResponse.setTotalProductPrice(BigDecimal.valueOf(10));

        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(mapper.createResponseFromOrderItem(any(OrderItem.class))).thenReturn(orderItemResponse);

        OrderItemResponse response = orderService.orderProduct(1, orderRequest);
        assertEquals(orderItemResponse, response);
    }

    @Test
    void throwExceptionIfQuantityLessThanOne() {
        orderRequest.setQuantity(0);
        assertThatThrownBy(() -> orderService.orderProduct(1, orderRequest))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("Product quantity ordered can not be less than 1");
    }

    @Test
    void throwExceptionIfNameIsBlank() {
        orderRequest.setCustomerName("");
        assertThatThrownBy(() -> orderService.orderProduct(1, orderRequest))
                .isInstanceOf(ApiBadRequestException.class)
                .hasMessageContaining("Customer Name cannot be blank");
    }

    @Test
    void testOrderedItemAddedToProductStock() {
        orderRequest.setQuantity(10);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        assertThatThrownBy(() -> orderService.orderProduct(1, orderRequest))
                .isInstanceOf(ApiResourceNotFoundException.class)
                .hasMessageContaining("Available quantity is " + product.getProductQuantity());
    }
}
