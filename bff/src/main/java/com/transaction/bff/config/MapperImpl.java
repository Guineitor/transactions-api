package com.transaction.bff.config;

import java.io.IOException;

import com.transaction.bff.config.interfaces.Mapper;
import com.transaction.bff.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


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
