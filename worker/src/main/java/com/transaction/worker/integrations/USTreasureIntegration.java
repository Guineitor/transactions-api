package com.transaction.worker.integrations;

import com.transaction.worker.domain.ExchangeInfo;
import com.transaction.worker.domain.FiscalDataResult;
import com.transaction.worker.integrations.interfaces.ExchangeRateIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class USTreasureIntegration implements ExchangeRateIntegration {

    private static final String URL = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service/v1/accounting/od/rates_of_exchange";
    private final WebClient webClient;

    public String getBaseEndpoint() {
        return URL;
    }

    public String getEndpoint(LocalDateTime date) {
        return "record_date:gte:" + DateTimeFormatter.ofPattern("yyyy-MM-dd").format(date.minusMonths(6));
    }

    public <T> Mono <FiscalDataResult> getExchangeRateByDate(String endpoint) {
        return (Mono<FiscalDataResult>) webClient.get()
                .uri(URLDecoder.decode(endpoint, StandardCharsets.UTF_8))
                .retrieve()
                .bodyToMono((Class<T>) FiscalDataResult.class)
                .doOnSuccess(t -> log.info("response: " + t)).switchIfEmpty(Mono.empty());
    }
}
