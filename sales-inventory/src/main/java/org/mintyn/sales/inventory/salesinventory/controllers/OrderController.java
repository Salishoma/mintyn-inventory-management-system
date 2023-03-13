package org.mintyn.sales.inventory.salesinventory.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.mintyn.sales.inventory.salesinventory.dto.requestdto.OrderRequest;
import org.mintyn.sales.inventory.salesinventory.dto.responsedto.OrderItemResponse;
import org.mintyn.sales.inventory.salesinventory.services.OrderService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request was successful"),
        @ApiResponse(responseCode = "400", description = "This is a bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
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
        orderService.checkoutOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(
            value = "{orderId}", method = RequestMethod.GET,
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<List<OrderItemResponse>> getOrderItems(
            @PathVariable final long orderId
    ) {
        return ResponseEntity.ok(orderService.orderItemResponse(orderId));
    }
}
