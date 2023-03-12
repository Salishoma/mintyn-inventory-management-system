package org.mintyn.sales.inventory.salesinventory.dto.responsedto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;

    private String productName;

    private BigDecimal price;

    private Integer productQuantity;

    private String description;

}
