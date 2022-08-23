package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.TransferType;

public interface TransferTypeDao {
    TransferType getTransferTypeByDescription(String description);
    TransferType getTransferTypeById(int transferId);
}