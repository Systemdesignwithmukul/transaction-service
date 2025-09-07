package com.riskplatform.transaction_service.mapper;



import com.riskplatform.transaction.avro.TransactionEvent;
import com.riskplatform.transaction.avro.TransactionType;
import com.riskplatform.transaction_service.dto.TransactionRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TransactionEventMapper {

    public TransactionEvent toEvent(String accountId,TransactionRequest transaction) {
        return TransactionEvent.newBuilder()
                .setAccountId(accountId)
                .setAmount(transaction.getAmount())
                .setTransactionType(mapTransactionType(transaction.getTransactionType()))
                .setTimestamp(Instant.now())
                .build();
    }

    private TransactionType mapTransactionType(
            com.riskplatform.transaction_service.dto.TransactionType dtoType) {
        return TransactionType.valueOf(dtoType.name());
    }
}
