package org.mintyn.inventory.response.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;

    private String customerName;
    private String customerPhoneNumber;

    private long productId;
    private String productName;
    private int quantity;

    private BigDecimal price;
    private BigDecimal totalPrice;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate orderCreatedDate;

}
