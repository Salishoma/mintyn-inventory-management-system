package org.mintyn.order.report.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class OrderReportResponse {
    private String message;
    private HttpStatus status;
}
