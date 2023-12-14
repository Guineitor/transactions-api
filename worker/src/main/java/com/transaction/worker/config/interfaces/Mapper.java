package com.transaction.worker.config.interfaces;

import com.transaction.worker.domain.Transaction;

import java.io.IOException;

public interface Mapper {
      Transaction parse(String transaction) throws IOException;
}
