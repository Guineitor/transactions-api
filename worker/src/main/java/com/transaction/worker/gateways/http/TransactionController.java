package com.transaction.worker.gateways.http;

import com.rabbitmq.client.Return;
import com.transaction.worker.domain.Transaction;
import com.transaction.worker.gateways.http.interfaces.BaseController;
import com.transaction.worker.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TransactionController implements BaseController {

    private final TransactionService service;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Transaction> find(@PathVariable final String id){
        log.info("find a transaction {}", id);
        return service.find(id)
                .doOnSuccess(t -> log.info("transaction found {}", t))
                .doOnError(e -> log.error("transaction not found {}", e.getMessage()))
                .switchIfEmpty(Mono.empty());
    }

    @GetMapping("{id}/{country}")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Transaction> findCountryCurrency(@PathVariable final String id,
                                                  @PathVariable final String country){
        log.info("find a transaction {}", id);
        return service.find(id, country)
                .doOnSuccess(t -> log.info("transaction found {}", t))
                .doOnError(e -> log.error("transaction not found {}", e.getMessage()))
                .switchIfEmpty(Mono.empty());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public Flux<List<Transaction>> findAll(){
        log.info("finding all transactions");
        return service.findAll()
                .doOnNext(t -> log.info("transactions found: {}", t.stream().count()))
                .doOnError(e -> log.error("transactions not found {}", e.getMessage()));
    }

}
