package com.transaction.bff.gateways.queue.interfaces;

import com.transaction.bff.domain.Transaction;
import reactor.core.publisher.Mono;

public interface TransactionGateway {
    Mono<Void> sendMessage(Transaction transaction);
}
