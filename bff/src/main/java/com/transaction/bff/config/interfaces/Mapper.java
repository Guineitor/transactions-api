package com.transaction.bff.config.interfaces;

import java.io.IOException;
import com.transaction.bff.domain.Transaction;

public interface Mapper {
      Transaction parse(String transaction) throws IOException;
}
