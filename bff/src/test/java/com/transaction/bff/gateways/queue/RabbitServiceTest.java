package com.transaction.bff.gateways.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.bff.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RabbitServiceTest {

    public static final String JSON = "{'id'':'123', 'description':'test', 'amount':null, 'transactionDate':null}";
    @InjectMocks
    private RabbitService rabbitService;

    @Mock
    private  RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @Test
    void sendMessage() throws Exception{

        Transaction transaction = new Transaction("123", "test", null, new BigDecimal(10));
        Mockito.when(objectMapper.writeValueAsString(Mockito.any(Transaction.class))).thenReturn(JSON);

        rabbitService.sendMessage(transaction);

        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(Mockito.any(), Mockito.anyMap());

    }
}