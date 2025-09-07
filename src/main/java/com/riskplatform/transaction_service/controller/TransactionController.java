package com.riskplatform.transaction_service.controller;


import com.riskplatform.transaction_service.api.TransactionsApi;
import com.riskplatform.transaction_service.dto.TransactionRequest;
import com.riskplatform.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionsApi {

    private final TransactionService transactionService;


    @Override
    public ResponseEntity<Void> createTransaction(String accountId, TransactionRequest transactionRequest) {
        transactionService.processTransaction(accountId,transactionRequest);
        return ResponseEntity.ok().build();
    }
}
