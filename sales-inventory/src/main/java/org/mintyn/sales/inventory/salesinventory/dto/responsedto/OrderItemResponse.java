package org.mintyn.sales.inventory.salesinventory.dto.responsedto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.mintyn.sales.inventory.salesinventory.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class OrderItemResponse {

    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal totalProductPrice;
    private Integer orderQuantity;
    private LocalDate createdAt;
    private OrderStatus status;

}
