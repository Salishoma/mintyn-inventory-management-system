package org.mintyn.sales.inventory.salesinventory.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    @NotBlank(message = "Product should have a name")
    private String productName;

    @Positive(message = "Price cannot be negative")
    private BigDecimal price;

    @Positive(message = "Product quantity cannot be negative")
    private Integer productQuantity;

    private String description;
}
