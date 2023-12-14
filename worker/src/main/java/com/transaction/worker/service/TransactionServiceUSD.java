package com.transaction.worker.service;

import com.transaction.worker.domain.Transaction;
import com.transaction.worker.repository.TransactionRepository;
import com.transaction.worker.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceUSD implements TransactionService {

    private final TransactionRepository repository;
    @Override
    public Mono<Transaction> save(Transaction transaction) {
        log.info("saving transaction - desc {} amount {}", transaction.getDescription(), transaction.getAmount());
        return repository.save(transaction);
    }

    @Override
    public Mono<Void> confirmation(Transaction transaction) {
        return Mono.empty();
    }
}
