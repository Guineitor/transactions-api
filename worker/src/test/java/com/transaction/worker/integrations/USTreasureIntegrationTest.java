package com.transaction.worker.integrations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class USTreasureIntegrationTest {

    @InjectMocks
    private USTreasureIntegration uSTreasureIntegration;

    private LocalDateTime transactionDate;

    @BeforeEach
    void setUp() {
        transactionDate = OffsetDateTime.parse(
                "Fri, 07 Aug 2020 18:00:00 +0000" ,
                DateTimeFormatter.RFC_1123_DATE_TIME
        ).toLocalDateTime();
    }
    @Mock
    private WebClient webClient;

    @Test
    void getEndpoint() {
        assertEquals("record_date:gte:2020-02-07", uSTreasureIntegration.getEndpoint(transactionDate));
    }
}