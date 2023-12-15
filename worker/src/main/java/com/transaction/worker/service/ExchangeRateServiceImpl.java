package com.transaction.worker.service;

import com.transaction.worker.domain.ExchangeInfo;
import com.transaction.worker.domain.FiscalDataResult;
import com.transaction.worker.integrations.interfaces.ExchangeRateIntegration;
import com.transaction.worker.service.interfaces.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    public static final String FILTER = "filter";
    private final ExchangeRateIntegration usExchangeRateIntegration;
    @Override
    public FiscalDataResult getExchangeRateByDate(LocalDateTime transactionDate) {
        String endpoint = UriComponentsBuilder
                .fromUriString(usExchangeRateIntegration.getBaseEndpoint())
                .queryParam(FILTER, usExchangeRateIntegration.getEndpoint(transactionDate))
                .encode().toUriString();
        return usExchangeRateIntegration.getExchangeRateByDate(endpoint).block();
    }

    @Override
    public ExchangeInfo calculateLocalAmount(FiscalDataResult fiscalDataResult,
                                             BigDecimal amountInUSD,
                                             String country) {

        ExchangeInfo exchangeInfo = fiscalDataResult
                .getData()
                .stream()
                .filter(i -> i.getCountry().equals(country))
                .sorted(Collections.reverseOrder((i1, i2) -> i2.getRecordDate().compareTo(i1.getRecordDate())))
                .findFirst().
                orElseThrow();
        exchangeInfo
                .setAmountInLocalCurrency(exchangeInfo.getExchangeRate()
                .multiply(amountInUSD)
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
        return exchangeInfo;
    }
}
