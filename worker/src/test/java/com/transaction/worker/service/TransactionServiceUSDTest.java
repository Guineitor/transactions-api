package com.transaction.worker.service;

import com.transaction.worker.domain.Transaction;
import com.transaction.worker.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceUSDTest {

    @InjectMocks
    private TransactionServiceUSD transactionService;

    @Mock
    private TransactionRepository repository;


    @Test
    void shouldSaveTransaction() {
        Transaction transaction = new Transaction("123", "test", LocalDateTime.now(), new BigDecimal(10));
        Mockito.when(repository.save(Mockito.any(Transaction.class))).thenReturn(Mono.just(transaction));
        Mono<Transaction> savedTransaction = transactionService.save(transaction);

        savedTransaction.subscribe(t -> {
            assertEquals("123", t.getId());
            assertEquals("test", t.getDescription());
            assertEquals(new BigDecimal(10), t.getAmount());
        });

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Transaction.class));
    }

    @Test
    void confirmation() {
    }
}