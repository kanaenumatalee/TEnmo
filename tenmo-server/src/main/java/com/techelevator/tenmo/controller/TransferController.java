package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.InsufficientBalanceException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import org.springframework.beans.factory.annotation.Autowired;
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
    TransferStatusDao transferStatusDAO;
    @Autowired
    AccountDao accountDao;

    // makeTransfer
    @RequestMapping(path = "{id}", method = RequestMethod.POST)
    public void makeTransfer(@RequestBody Transfer transfer, @PathVariable long id) throws InsufficientBalanceException {

        BigDecimal transferAmount = transfer.getAmount();
        Account accountFrom = accountDao.getAccountByAccountId(transfer.getAccountFrom());
        Account accountTo = accountDao.getAccountByAccountId(transfer.getAccountTo());

        accountFrom.sendMoney(transferAmount);
        accountTo.getMoney(transferAmount);

        accountDao.updateAccount(accountFrom);
        accountDao.updateAccount(accountTo);
        transferDao.makeTransfer(transfer);
    }

    @RequestMapping(path = "getTransfersByUserId/{id}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByUserId(@PathVariable int userId){
        return transferDao.getTransfersByUserId(userId);
    }

    @RequestMapping(path = "getTransferByTransferId/{id}", method = RequestMethod.GET)
    public Transfer getTransferByTransferId(@PathVariable int transferId){
        return transferDao.getTransferByTransferId(transferId);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(){
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path ="updateTransfer/{id}",method = RequestMethod.PUT)
    public boolean updateTransfer(@RequestBody Transfer transfer){
        return transferDao.updateTransfer(transfer);
    }


    // getPendingTransfers
    @RequestMapping(path = "getPendingTransfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(@PathVariable int userId ){
        return transferDao.getPendingTransfers(userId);
    }



    //TransferStatusDao
    @RequestMapping(path="/transferstatus/{description}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusByDescription(@RequestParam String description) {
        return transferStatusDAO.getTransferStatusByDesc(description);
    }

    @RequestMapping(path="/transferstatus/{id}", method = RequestMethod.GET)
    public TransferStatus getTransferStatusFromId(@PathVariable int id) {
        return transferStatusDAO.getTransferStatusById(id);
    }



    //transferTypeDao
    @RequestMapping(path="/transfertype/{description}", method = RequestMethod.GET)
    public TransferType getTransferTypeFromDescription(@RequestParam String description) {
        return transferTypeDao.getTransferTypeFromDescription(description);
    }


    @RequestMapping(path="/transfertype/{id}", method = RequestMethod.GET)
    public TransferType getTransferDescFromId(@PathVariable int id)  {
        return transferTypeDao.getTransferTypeFromId(id);
    }


}