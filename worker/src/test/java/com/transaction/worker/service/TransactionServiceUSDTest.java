package com.transaction.worker.service;

import com.transaction.worker.domain.Transaction;
import com.transaction.worker.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        Transaction transaction = new Transaction("123", "test", LocalDateTime.now(), new BigDecimal(10), null);
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
    void shouldNotSaveTransactionWhenDescriptionOverFlow() {
        Transaction transaction = new Transaction(
                "123",
                "not_valid_description_not_valid_description_not_valid_description_not_valid_description_not_valid_description",
                LocalDateTime.now(),
                new BigDecimal(10),
                null);

        transactionService.save(transaction);

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Transaction.class));
    }

    @Test
    void shouldNotSaveTransactionWhenTransactionDateNull() {
        Transaction transaction = new Transaction(
                "",
                "valid_description",
                null,
                new BigDecimal(1000),
                null);

        transactionService.save(transaction);

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Transaction.class));
    }

    @Test
    void shouldNotSaveTransactionWhenAmountNull() {
        Transaction transaction = new Transaction(
                "",
                "valid_description",
                LocalDateTime.now(),
                null,
                null);

        transactionService.save(transaction);

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(Transaction.class));
    }

    @Test
    void confirmation() {
    }
}