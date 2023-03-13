package org.mintyn.order.report.services;

import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.dtos.OrderReportResponse;

import java.time.LocalDate;
import java.util.List;

public interface OrderReportService {

    void saveOrderReport(List<OrderResponse> orderResponseList);
    OrderReportResponse generateOrderReport(LocalDate from, LocalDate to);
}
