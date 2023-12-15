package com.transaction.worker.service;

import com.transaction.worker.domain.ExchangeInfo;
import com.transaction.worker.domain.FiscalDataResult;
import com.transaction.worker.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

    public static final String BRAZIL = "Brazil";
    public static final String AM_VIRGIN_ISLAND = "ILHAS VIRGENS AMERICANAS";
    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Test
    void calculateLocalAmount() {
        FiscalDataResult fiscalDataResult =  new FiscalDataResult();
        var info = new ExchangeInfo();
        info.setExchangeRate(new BigDecimal(4.858));
        info.setCountry(BRAZIL);
        fiscalDataResult.setData(List.of(info));
        var amountUSD = new BigDecimal(1000.50);
        Transaction t = new Transaction("123", "test", null, amountUSD, info);
        ExchangeInfo calculated = exchangeRateService.calculateLocalAmount(fiscalDataResult, amountUSD, BRAZIL);
        assertEquals(new BigDecimal(4860.43).setScale(2, BigDecimal.ROUND_HALF_UP), calculated.getAmountInLocalCurrency());
    }
    @Test
    void shouldNotCalculateLocalAmount() {
        FiscalDataResult fiscalDataResult =  new FiscalDataResult();
        var info = new ExchangeInfo();
        info.setExchangeRate(new BigDecimal(4.858));
        info.setCountry(AM_VIRGIN_ISLAND);
        fiscalDataResult.setData(List.of(info));
        var amountUSD = new BigDecimal(1000.50);
        assertThrows(NoSuchElementException.class,
                () -> exchangeRateService.calculateLocalAmount(fiscalDataResult, amountUSD, BRAZIL));

    }
}