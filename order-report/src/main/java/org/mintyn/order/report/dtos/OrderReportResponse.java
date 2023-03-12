package org.mintyn.order.report.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class OrderReportResponse {
    private String message;
    private HttpStatus status;
}
