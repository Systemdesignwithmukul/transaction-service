package com.riskplatform.transaction_service.service;

import com.riskplatform.transaction_service.dto.TransactionRequest;

public interface KafkaProducerService {
    void produceTransactionEvent(String accountId, TransactionRequest transactionRequest);
}
