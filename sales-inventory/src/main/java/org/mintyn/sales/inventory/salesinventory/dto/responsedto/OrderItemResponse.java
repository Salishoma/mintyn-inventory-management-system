package org.mintyn.sales.inventory.salesinventory.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mintyn.sales.inventory.salesinventory.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private String productName;
    private BigDecimal productPrice;
    private BigDecimal totalProductPrice;
    private Integer orderQuantity;
    private LocalDate createdAt;
    private OrderStatus status;

}
