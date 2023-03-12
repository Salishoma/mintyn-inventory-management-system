package org.mintyn.order.report.kafka;

import org.mintyn.inventory.response.model.OrderResponse;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "orderReport", groupId = "mintyn", containerFactory = "orderReportListenerContainerFactory")
    public void listener(List<OrderResponse> responseList) {
        System.out.println("Listener receives data: " + responseList + " 🎉");
    }
}
