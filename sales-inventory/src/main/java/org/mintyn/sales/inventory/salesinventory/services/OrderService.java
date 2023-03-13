package org.mintyn.sales.inventory.salesinventory.services;

import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;

import java.util.List;

public interface OrderService {
    OrderItemResponse orderProduct(long productId, OrderRequest request);
    List<OrderItemResponse> removeOrderedItem(long orderItemId);
    List<OrderItemResponse> checkoutOrder(long orderId);
    List<OrderItemResponse> orderItemResponse(long orderId);
}
