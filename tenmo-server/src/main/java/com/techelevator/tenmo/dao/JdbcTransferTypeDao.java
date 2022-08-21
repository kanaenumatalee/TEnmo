package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JdbcTransferTypeDao implements TransferTypeDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public TransferType getTransferTypeFromDescription(String description) {
        return null;
    }

    @Override
    public TransferType getTransferTypeFromId(int transferId) {
        return null;
    }
}
