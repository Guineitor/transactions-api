package com.transaction.bff.config;

import com.transaction.bff.exception.TransactionNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerHandler {
  @ExceptionHandler(TransactionNotSavedException.class)
  public ResponseEntity<String> handleNotSaved(final TransactionNotSavedException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.METHOD_FAILURE);
  }
}
