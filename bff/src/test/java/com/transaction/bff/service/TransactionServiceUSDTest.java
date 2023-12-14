package com.transaction.bff.service;

import com.transaction.bff.domain.Transaction;
import com.transaction.bff.exception.TransactionNotSavedException;
import com.transaction.bff.gateways.queue.interfaces.TransactionGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceUSDTest {

    @InjectMocks
    private TransactionServiceUSD transactionServiceUSD;
    @Mock
    private TransactionGateway transactionGateway;

    private LocalDateTime transactionDate;

    @BeforeEach
    void setUp() {
        transactionDate = OffsetDateTime.parse(
                "Fri, 07 Aug 2020 18:00:00 +0000" ,
                DateTimeFormatter.RFC_1123_DATE_TIME
        ).toLocalDateTime();

    }


    @Test
    void save() {
        Transaction transaction = new Transaction("123", "test", transactionDate , new BigDecimal(10));
        Mockito.when(transactionGateway.sendMessage(transaction)).thenReturn(Mono.empty());
        transactionServiceUSD.save(transaction);
        Mockito.verify(transactionGateway, Mockito.times(1)).sendMessage(transaction);
    }

    @Test
    void saveError() {
        Transaction transaction = new Transaction("123", "test", transactionDate , new BigDecimal(10));
        Mockito.when(transactionGateway.sendMessage(transaction)).thenThrow(TransactionNotSavedException.class);
        assertThrows(TransactionNotSavedException.class,
                () -> transactionServiceUSD.save(transaction));

        Mockito.verify(transactionGateway, Mockito.times(1)).sendMessage(transaction);

    }
}