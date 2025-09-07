package com.riskplatform.transaction_service.service;

import com.riskplatform.transaction_service.dto.TransactionRequest;

public interface TransactionService {
    void processTransaction(String accountId, TransactionRequest transactionRequest);
}
