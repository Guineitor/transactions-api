package com.transaction.worker.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.worker.config.interfaces.Mapper;
import com.transaction.worker.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class MapperImpl implements Mapper {


    private ObjectMapper objectMapper;

    @Autowired
    public MapperImpl (ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Transaction parse(String transaction) throws  IOException {
         return objectMapper.readValue(transaction, Transaction.class);
    }
    
}
