package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferStatus;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferStatusDao implements TransferStatusDAO {
    private JdbcTemplate jdbcTemplate;

    @Override
    public TransferStatus getTransferStatusByDesc(String description) {
        return null;
    }

    @Override
    public TransferStatus getTransferStatusById(int transferStatusId) {
        return null;
    }
}
