package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.InsufficientBalanceException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import java.util.List;


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer/")
public class TransferController {
    @Autowired
    UserDao userDao;
    @Autowired
    TransferDao transferDao;
    @Autowired
    TransferTypeDao transferTypeDao;
    @Autowired
    TransferStatusDao transferStatusDao;
    @Autowired
    AccountDao accountDao;

    // makeTransfer
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "users/{id}", method = RequestMethod.POST)
    public void makeTransfer(@RequestBody Transfer transfer, @PathVariable long id) throws InsufficientBalanceException {
        BigDecimal transferAmount = transfer.getAmount();
        Account accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());
        if(transfer.getTransferStatusId() == transferStatusDao.getTransferStatusByDesc("Approved").getTransferStatusId()) {
            accountFrom.sendMoney(transferAmount);
            accountTo.getMoney(transferAmount);
            accountDao.updateAccount(accountFrom);
            accountDao.updateAccount(accountTo);
        }
        transferDao.makeTransfer(transfer);
    }

    @RequestMapping(path = "users/{userId}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int userId){
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "{transferId}", method = RequestMethod.POST)
    public Transfer makeTransferByTransferId(@PathVariable int transferId){
        return transferDao.getTransferByTransferId(transferId);
    }

    @RequestMapping(path = "{transferId}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transferId){
        return transferDao.getTransferByTransferId(transferId);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(){
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path ="users/{id}",method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer){
        return transferDao.updateTransfer(transfer);
    }

    @RequestMapping(path = "users/{userId}/pending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(@PathVariable int userId){
        return transferDao.getPendingTransfers(userId);
    }

    @RequestMapping(path="transfer_status/desc/{description}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusByDescription(@PathVariable String description) {
        return transferStatusDao.getTransferStatusByDesc(description);
    }

    @RequestMapping(path="transfer_status/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusById(@PathVariable int id) {
        return transferStatusDao.getTransferStatusById(id);
    }

    @RequestMapping(path="transfer_type/desc/{description}", method = RequestMethod.GET)
    public TransferType getTransferTypeByDescription(@PathVariable String description) {
        return transferTypeDao.getTransferTypeByDescription(description);
    }

    @RequestMapping(path="transfer_type/{transferTypeId}", method = RequestMethod.GET)
    public TransferType getTransferTypeById(@PathVariable int transferTypeId)  {
        return transferTypeDao.getTransferTypeById(transferTypeId);
    }

    @RequestMapping(path="transfers/{id}", method = RequestMethod.PUT)
    public void updateTransferStatus(@RequestBody Transfer transfer, @PathVariable int id) throws InsufficientBalanceException {
        // only go through with the transfer if approved
        if(transfer.getTransferStatusId() == transferStatusDao.getTransferStatusByDesc("Approved").getTransferStatusId()) {
            BigDecimal amountToTransfer = transfer.getAmount();
            Account accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
            Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());
            accountFrom.sendMoney(amountToTransfer);
            accountTo.getMoney(amountToTransfer);
            transferDao.updateTransfer(transfer);
            accountDao.updateAccount(accountFrom);
            accountDao.updateAccount(accountTo);
        } else {
            transferDao.updateTransfer(transfer);
        }
    }
}
