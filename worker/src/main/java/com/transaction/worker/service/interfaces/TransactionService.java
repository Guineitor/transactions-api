package com.transaction.worker.service.interfaces;

import com.transaction.worker.domain.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Transaction> save(Transaction transaction);

    Mono<Void> confirmation(Transaction transaction);

}
