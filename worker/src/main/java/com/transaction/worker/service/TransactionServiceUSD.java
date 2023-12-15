package com.transaction.worker.service;

import com.transaction.worker.domain.FiscalDataResult;
import com.transaction.worker.domain.Transaction;
import com.transaction.worker.repository.TransactionRepository;
import com.transaction.worker.service.interfaces.ExchangeRateService;
import com.transaction.worker.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceUSD implements TransactionService {

    private final TransactionRepository repository;
    private final ExchangeRateService exchangeRateService
            ;
    @Override
    public Mono<Transaction> save(Transaction transaction) {
        if(!isValidTransaction(transaction))
            return Mono.error(new RuntimeException("invalid transaction " ));
        return repository.save(transaction)
                .doOnSuccess(t -> log.info("transaction saved {}", t))
                .doOnError(e -> log.error("transaction not saved {}", e.getMessage()));
    }

    @Override
    public Mono<Transaction> find(String id) {
        return repository.findById(id)
                .doOnSuccess(t -> log.info("transaction found {}", t))
                .doOnError(e -> {
                    log.error("transaction not found {}", e.getMessage());
                    throw new RuntimeException(id);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Transaction> find(String id, String country) {

        return repository.findById(id)
                .doOnSuccess(t -> {
                    log.info("transaction found {}", t.getId());
                    FiscalDataResult exchangeRate = exchangeRateService
                            .getExchangeRateByDate(t.getTransactionDate());

                    t.setExchangeInfo(exchangeRateService.calculateLocalAmount(exchangeRate,
                            t.getAmount(), country));

                } )
                .doOnError(e -> {
                    log.error("transaction not found {}", e.getMessage());
                    throw new RuntimeException(id);
                });
    }

    @Override
    public Flux<List<Transaction>> findAll() {
        return repository.findAll().collectList().flux().switchIfEmpty(Flux.empty());

    }

    @Override
    public Mono<Void> confirmation(Transaction transaction) {
        return Mono.empty();
    }

    private  static boolean isValidTransaction(Transaction t) {
        if(t.getAmount() == null)
            return false;
        if(t.getDescription().length() > 50)
            return false;
        if(t.getTransactionDate() == null)
            return false;
        return true;
    }
}
