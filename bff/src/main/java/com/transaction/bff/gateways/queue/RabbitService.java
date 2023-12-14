package com.transaction.bff.gateways.queue;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.bff.domain.Transaction;
import com.transaction.bff.gateways.queue.interfaces.TransactionGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitService implements TransactionGateway {

    @Value("${client.queue:transaction-message-queue}")
    private String queue;
    @Value("${client.key:body}")
    private String key;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> sendMessage(final Transaction transaction) {
        Map<String, String> map = new HashMap<>();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            String jsonString = objectMapper.writeValueAsString(transaction);
            log.info( "sending transaction in body message: {}", jsonString);
            map.put(key, jsonString);
        }catch(Exception ex){
            log.error(ex.getMessage(), ex);
        }

        rabbitTemplate.convertAndSend(queue, map);
        return Mono.empty();
    }
}
