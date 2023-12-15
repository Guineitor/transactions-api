package com.transaction.worker.exceptions;


public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(final String id) {
    super(String.format("transactions[%s].notFound", id));
  }

}
