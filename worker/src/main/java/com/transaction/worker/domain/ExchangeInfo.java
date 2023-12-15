package com.transaction.worker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfo {

    @JsonProperty("country")
    private String country;
    @JsonProperty("exchange_rate")
    private BigDecimal exchangeRate;
    private BigDecimal amountInLocalCurrency;
    @JsonProperty("record_date")
    private String recordDate;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("country_currency_desc")
    private String countryCurrencyDesc;
    @JsonProperty("effective_date")
    private String effectiveDate;

}
