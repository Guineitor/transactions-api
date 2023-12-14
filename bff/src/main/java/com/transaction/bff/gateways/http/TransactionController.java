package com.transaction.bff.gateways.http;

import com.transaction.bff.domain.Transaction;
import com.transaction.bff.gateways.http.interfaces.BaseController;
import com.transaction.bff.service.interfaces.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@RestController
public class TransactionController implements BaseController {

    private final TransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@Validated @RequestBody final Transaction transaction){
        return service.save(transaction)
                .doOnNext(t -> log.info("creating new transaction - desc {}", transaction.getDescription()));
    }
}
