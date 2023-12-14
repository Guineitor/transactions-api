package com.transaction.bff.exception;

import com.transaction.bff.domain.Transaction;

public class TransactionNotSavedException extends RuntimeException {

  public TransactionNotSavedException(final Transaction transaction) {
    super(String.format("transactions[%s].notSaved", transaction.getAmount()));
  }

}
