package org.mintyn.sales.inventory.salesinventory.kafka.orderreports;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.mintyn.inventory.response.exception.ApiBadRequestException;
import org.mintyn.inventory.response.model.OrderResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderReportSenderServiceImpl implements OrderReportSenderService {
    private final KafkaTemplate<String, List<OrderResponse>> kafkaTemplate;

    @Override
    public void sendOrderReport(List<OrderResponse> orderResponseList) {
        CompletableFuture<SendResult<String, List<OrderResponse>>> completableFuture =
                kafkaTemplate.send("orderReport", orderResponseList);

        completableFuture.whenComplete((result, ex) -> {
            if (ex != null) {
                throw new ApiBadRequestException("Incomplete order");
            } else {

                RecordMetadata metadata = result.getRecordMetadata();
                log.info("topic: {}, partition: {}, offset: {}, timestamp: {}, key: {}, value: {}",
                        metadata.topic(), metadata.partition(), metadata.hasOffset(),
                        metadata.hasTimestamp(), metadata.serializedKeySize(), metadata.serializedValueSize());
            }
        });
    }
}
