package com.transaction.bff.service.interfaces;

import com.transaction.bff.domain.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionService {

    Mono<Void> save(final Transaction transaction);
}
