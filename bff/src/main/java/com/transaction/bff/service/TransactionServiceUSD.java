package com.transaction.bff.service;

import com.transaction.bff.domain.Transaction;
import com.transaction.bff.exception.TransactionNotSavedException;
import com.transaction.bff.gateways.queue.interfaces.TransactionGateway;
import com.transaction.bff.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceUSD implements TransactionService {

    public static final String ERROR_MESSAGE = "error sending transaction to queue - desc {}";
    private final TransactionGateway transactionGateway;
    @Override
    public Mono<Void> save(final Transaction transaction) {

        transactionGateway.sendMessage(transaction)
                .doOnSuccess(t -> log.info("sending transaction to queue - desc {}", transaction.getDescription()))
                .doOnError(t -> {
                    log.error(ERROR_MESSAGE, transaction.getDescription());
                    throw new TransactionNotSavedException(transaction);
                })
                .subscribe();

        return Mono.empty();
    }
}
