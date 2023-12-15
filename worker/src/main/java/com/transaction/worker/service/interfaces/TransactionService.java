package com.transaction.worker.service.interfaces;

import com.transaction.worker.domain.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface TransactionService {

    Mono<Transaction> save(Transaction transaction);

    Mono<Transaction> find(final String id);

    Mono<Transaction> find(final String id, final String country);

    Flux<List<Transaction>> findAll();

    Mono<Void> confirmation(final Transaction transaction);

}
