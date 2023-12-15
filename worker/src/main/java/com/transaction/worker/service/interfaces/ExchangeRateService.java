package com.transaction.worker.service.interfaces;

import com.transaction.worker.domain.ExchangeInfo;
import com.transaction.worker.domain.FiscalDataResult;
import com.transaction.worker.domain.Transaction;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ExchangeRateService {
    FiscalDataResult getExchangeRateByDate(LocalDateTime transactionDate);
    ExchangeInfo calculateLocalAmount(FiscalDataResult fiscalDataResult, BigDecimal amountInUSD,String country);
}
