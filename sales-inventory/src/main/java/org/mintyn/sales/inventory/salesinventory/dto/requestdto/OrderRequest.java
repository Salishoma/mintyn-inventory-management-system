package org.mintyn.sales.inventory.salesinventory.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderRequest {
    @NotBlank(message = "Product should have a name")
    private String customerName;

    private String customerPhoneNumber;

    @Positive(message = "Product quantity cannot be negative")
    private Integer quantity;

}
