package com.transaction.worker.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transactions")
public class Transaction {

    @Id
    @NotNull
    private String id;
    @Size(max=50, message="Description must have max 50 characters")
    @NotBlank(message = "Description is mandatory field")
    private String description;
    @NotBlank(message = "Description is mandatory field")
    private LocalDateTime transactionDate;
    @NotBlank
    private BigDecimal amount;
    private ExchangeInfo exchangeInfo;
}
