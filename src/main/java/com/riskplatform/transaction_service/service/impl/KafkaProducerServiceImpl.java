package com.riskplatform.transaction_service.service.impl;

import com.riskplatform.transaction.avro.TransactionEvent;
import com.riskplatform.transaction_service.dto.TransactionRequest;
import com.riskplatform.transaction_service.mapper.TransactionEventMapper;
import com.riskplatform.transaction_service.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final TransactionEventMapper eventMapper;

    @Value("${spring.kafka.transaction-events.topic}")
    private String transactionEventsTopic;

    @Override
    public void produceTransactionEvent(String accountId, TransactionRequest transactionRequest) {
        log.info("Publishing transaction event for account ID: {}, transaction: {}",accountId, transactionRequest);

        CompletableFuture<SendResult<String, TransactionEvent>> future =
                kafkaTemplate.send(transactionEventsTopic, eventMapper.toEvent(accountId,transactionRequest));

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Successfully published transaction event: {} to topic: {} with offset: {}",
                        accountId,
                        transactionEventsTopic,
                        result.getRecordMetadata().offset());
            } else {
                log.error("Failed to publish transaction event: {}", transactionRequest, ex);
            }
        });
    }
}
