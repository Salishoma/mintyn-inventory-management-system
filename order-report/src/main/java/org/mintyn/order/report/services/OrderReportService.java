package org.mintyn.order.report.services;

import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.dtos.OrderReportResponse;

import java.time.LocalDate;

public interface OrderReportService {

    void saveOrderReport(OrderResponse orderResponse);
    OrderReportResponse getOrderReport(LocalDate from, LocalDate to);
}
