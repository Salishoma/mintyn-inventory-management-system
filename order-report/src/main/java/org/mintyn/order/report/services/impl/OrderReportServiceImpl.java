package org.mintyn.order.report.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.response.exception.ApiResourceNotFoundException;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.entities.OrderReport;
import org.mintyn.order.report.repositories.OrderReportRepository;
import org.mintyn.order.report.dtos.OrderReportResponse;
import org.mintyn.order.report.services.OrderReportService;
import org.mintyn.order.report.utils.ExcelFileGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderReportServiceImpl implements OrderReportService {
    private final OrderReportRepository orderReportRepository;

    private final ExcelFileGenerator excelFileGenerator;

    @Override
    public void saveOrderReport(final List<OrderResponse> orderResponseList) {
        for (OrderResponse orderResponse : orderResponseList) {
            OrderReport orderReport = OrderReport.builder()
                    .orderId(orderResponse.getOrderId())
                    .customerName(orderResponse.getCustomerName())
                    .customerPhoneNumber(orderResponse.getCustomerPhoneNumber())
                    .orderQuantity(orderResponse.getQuantity())
                    .productName(orderResponse.getProductName())
                    .productPrice(orderResponse.getPrice())
                    .totalProductPrice(orderResponse.getTotalPrice())
                    .orderCreatedDate(orderResponse.getOrderCreatedDate())
                    .build();
            orderReportRepository.save(orderReport);
        }
    }

    @Override
    public OrderReportResponse generateOrderReport(final LocalDate from, final LocalDate to){
        try {
            List<OrderReport> rangedOrderReport = orderReportRepository
                    .findAllByOrderCreatedDateIsBetween(from, to);

            excelFileGenerator.generateTerminalRequestData(rangedOrderReport, from + " to " + to);
            return new OrderReportResponse("Excel File Generated", HttpStatus.OK);
        }catch (Exception ex){
            throw new ApiResourceNotFoundException("Could not find orders within this date range");
        }
    }
}
