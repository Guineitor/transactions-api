package com.transaction.bff.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String id;
    private String description;
    private LocalDateTime transactionDate;
    private BigDecimal amount;
}
