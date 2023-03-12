package org.mintyn.order.report.services.impl;

import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.entities.OrderReport;
import org.mintyn.order.report.repositories.OrderReportRepository;
import org.mintyn.order.report.dtos.OrderReportResponse;
import org.mintyn.order.report.services.OrderReportService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderReportServiceImpl implements OrderReportService {
    private final OrderReportRepository orderReportRepository;

//    private final ExcelFileGenerator excelFileGenerator;

    @Override
    public void saveOrderReport(final OrderResponse orderResponse){
        System.out.println("=============>Order report: " + orderResponse);
//        OrderReport orderReport = orderReportMapper.mapToOrderReport(orderResponse);
//        orderReportRepository.save(orderReport);
//        log.info("ORDER REPORT WITH PRODUCT NAME {} HAS BEEN SAVED", orderResponse.getProductName());
    }

    @Override
    public OrderReportResponse getOrderReport(final LocalDate from, LocalDate to){
        return null;
//        try {
//            List<OrderReport> rangedOrderReport = orderReportRepository
//                    .findAllByOrderCreatedDateIsBetween(startDate, endDate);
//
//            excelFileGenerator.generateTerminalRequestData(rangedOrderReport, startDate + " to " + endDate);
//            return new ReportResponse("Excel File Generated", HttpStatus.OK);
//        }catch (Exception ex){
//            throw new ApiResourceNotFoundException("Could not find orders within this date range");
//        }
    }
}
