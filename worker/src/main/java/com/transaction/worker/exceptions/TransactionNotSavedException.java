package com.transaction.worker.exceptions;

import com.transaction.worker.domain.Transaction;

public class TransactionNotSavedException extends RuntimeException {

  public TransactionNotSavedException(final Transaction transaction) {
    super(String.format("transactions[%s].notSaved", transaction.getAmount()));
  }

}
