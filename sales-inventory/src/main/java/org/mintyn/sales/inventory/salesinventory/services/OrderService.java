package org.mintyn.sales.inventory.salesinventory.services;

import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;

public interface OrderService {
    OrderItemResponse orderProduct(long productId, OrderRequest request);
    OrderItemResponse removeOrderedIem(long orderItemId);
    void checkoutOrder(long orderId);
}
