package org.mintyn.sales.inventory.salesinventory.kafka.orderreports;

import org.mintyn.inventory.response.model.OrderResponse;

import java.util.List;

public interface OrderReportSenderService {
    void sendOrderReport(List<OrderResponse> orderResponseList);
}
