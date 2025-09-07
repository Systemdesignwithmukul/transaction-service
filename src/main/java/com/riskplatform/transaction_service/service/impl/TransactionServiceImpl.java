package com.riskplatform.transaction_service.service.impl;

import com.riskplatform.transaction_service.dto.TransactionRequest;
import com.riskplatform.transaction_service.entity.Transactions;
import com.riskplatform.transaction_service.repository.TransactionRepository;
import com.riskplatform.transaction_service.service.KafkaProducerService;
import com.riskplatform.transaction_service.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void processTransaction(String accountId, TransactionRequest transactionRequest) {
        log.info("Received transaction {} {}",accountId,transactionRequest.getAmount());

        Transactions transaction = Transactions.builder()
                .accountId(accountId)
                .amount(transactionRequest.getAmount())
                .transactionType(transactionRequest.getTransactionType())
                .timestamp(Instant.now())
                .build();
        transactionRepository.save(transaction);

        log.info("Record created {}",transactionRequest);

        kafkaProducerService.produceTransactionEvent(accountId,transactionRequest);
    }
}
