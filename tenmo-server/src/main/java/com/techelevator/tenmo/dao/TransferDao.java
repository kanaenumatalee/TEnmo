package com.techelevator.tenmo.dao;


import com.techelevator.tenmo.model.Transfer;

import java.util.List;


public interface TransferDao {
    void makeTransfer(Transfer transfer);
    List<Transfer> getTransfersByUserId(int userId);
    Transfer getTransferByTransferId(int transferId);
    List<Transfer> getAllTransfers();
    List<Transfer> getPendingTransfers(int userId);
    boolean updateTransfer(Transfer transfer);
}
