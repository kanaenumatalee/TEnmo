package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    void makeTransfer(Transfer transfer);
    List<Transfer> getTransfersByUserId(long userId);
    Transfer getTransferByTransferId(long transferId);
    List<Transfer> getAllTransfers();
    List<Transfer> getPendingTransfers(long userId);
    boolean updateTransfer(Transfer transfer);

}
