package org.mintyn.order.report.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request was successful"),
        @ApiResponse(responseCode = "400", description = "This is a bad request, please follow the API documentation for the proper request format."),
        @ApiResponse(responseCode = "500", description = "The server is down, please make sure that the Application is running")
})
public class OrderReportController {
    private final OrderReportService orderReportService;

    @GetMapping("/generate-report/{from}/{to}")
    public ResponseEntity<OrderReportResponse> generateReport(@DateTimeFormat(pattern = "dd-MM-yyyy")
                                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                            @PathVariable final LocalDate from,
                                                              @DateTimeFormat(pattern = "dd-MM-yyyy")
                                            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                            @PathVariable final LocalDate to){
        return new ResponseEntity<>(orderReportService.generateOrderReport(from, to), HttpStatus.OK);
    }
}
