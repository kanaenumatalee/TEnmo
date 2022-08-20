package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component

public class JdbcTransferDao implements TransferDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public void makeTransfer(Transfer transfer) {

    }

    @Override
    public List<Transfer> getTransfersByUserId(int userId) {
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer AS t " +
                "JOIN account AS a ON a.user_id = t.account_from " +
               "WHERE a.user_id = ?";
        SqlRowSet result =jdbcTemplate.queryForRowSet(sql,userId);
        List<Transfer> transfer = new ArrayList<>();
        while (result.next()){
            transfer.add(mapResultToTransfer(result));
        }
        return transfer;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId) {
        return null;
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return null;
    }

    @Override
    public List<Transfer> getPendingTransfers(int userId) {
        return null;
    }

    @Override
    public boolean updateTransfer(Transfer transfer) {return true;}

    private Transfer mapResultToTransfer(SqlRowSet result) {
        int transferId = result.getInt("transfer_id");
        int transferTypeId = result.getInt("transfer_type_id");
        int transferStatusId = result.getInt("transfer_status_id");
        int accountFrom = result.getInt("account_from");
        int accountTo = result.getInt("account_to");
        BigDecimal amount = result.getBigDecimal("amount");

        Transfer transfer = new Transfer(transferId, transferTypeId, transferStatusId, accountFrom, accountTo, amount);
        return transfer;
    }
    }