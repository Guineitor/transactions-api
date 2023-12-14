package com.transaction.worker.listeners;

import com.transaction.worker.config.interfaces.Mapper;
import com.transaction.worker.domain.Transaction;
import com.transaction.worker.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMessageListener {

        @Value("${client.key:body}")
        private String key;

        private final TransactionService service;
        private final  Mapper mapper;

        @RabbitListener(queues = "${client.queue:transaction-message-queue}")
        public void receiveMessage(Map<String, String> message) throws IOException {
            String body = message.get(key);
            Transaction transaction = mapper.parse(body) ;
            service.save(transaction)
                    .doOnNext(t -> service.confirmation(t))
                    .doOnSuccess(t -> log.info("Transaction saved: {}", t.getId()))
                    .subscribe();
        }
}
