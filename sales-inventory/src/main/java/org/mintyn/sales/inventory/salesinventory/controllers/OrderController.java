package org.mintyn.sales.inventory.salesinventory.controllers;

import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;
import org.mintyn.sales.inventory.salesinventory.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @RequestMapping(
            value = "products/{productId}", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<OrderItemResponse> orderProduct(
            @PathVariable final long productId,
            @RequestBody OrderRequest orderRequest
    ) {
        return ResponseEntity.ok(orderService.orderProduct(productId, orderRequest));
    }

    @RequestMapping(
            value = "remove/{orderItemId}", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<OrderItemResponse> removeOrderedIem(
            @PathVariable final long orderItemId
    ) {
        return ResponseEntity.ok(orderService.removeOrderedIem(orderItemId));
    }

    @RequestMapping(
            value = "checkout/{orderId}", method = RequestMethod.POST,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<?> checkoutOrderAndPublish(
            @PathVariable final long orderId
    ) {
        System.out.println("=======>orderId: " + orderId);
        orderService.checkoutOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
