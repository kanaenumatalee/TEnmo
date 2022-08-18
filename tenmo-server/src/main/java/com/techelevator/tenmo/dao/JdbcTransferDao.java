package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public class JdbcTransferDao implements TransferDao {
    @Override
    public void makeTransfer(Transfer transfer) {

    }

    @Override
    public List<Transfer> getTransfersByUserId(int userId) {
        return null;
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
    public void updateTransfer(Transfer transfer) {

    }
}
