package org.mintyn.order.report.kafka;

import lombok.RequiredArgsConstructor;
import org.mintyn.inventory.response.model.OrderResponse;
import org.mintyn.order.report.services.OrderReportService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final OrderReportService orderReportService;

    @KafkaListener(topics = "orderReport", groupId = "mintyn", containerFactory = "orderReportListenerContainerFactory")
    public void listener(List<OrderResponse> responseList) {

        orderReportService.saveOrderReport(responseList);
    }
}
