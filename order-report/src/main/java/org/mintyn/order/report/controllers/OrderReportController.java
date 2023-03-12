package org.mintyn.order.report.controllers;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.mintyn.order.report.dtos.OrderReportResponse;
import org.mintyn.order.report.services.OrderReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class OrderReportController {
    private final OrderReportService orderReportService;

    @GetMapping("/generate-report/{from}/{to}")
    public ResponseEntity<OrderReportResponse> generateReport(@DateTimeFormat(pattern = "dd-MM-yyyy")
                                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                            @PathVariable final LocalDate from,
                                                              @DateTimeFormat(pattern = "dd-MM-yyyy")
                                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                            @PathVariable final LocalDate to){
        return new ResponseEntity<>(orderReportService.getOrderReport(from, to), HttpStatus.OK);
    }
}
