package com.transaction.worker.integrations.interfaces;

import com.transaction.worker.domain.ExchangeInfo;
import com.transaction.worker.domain.FiscalDataResult;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public interface ExchangeRateIntegration {
    String getBaseEndpoint();

    String getEndpoint(LocalDateTime date);

    <T> Mono<FiscalDataResult> getExchangeRateByDate(String endpoint);
}
