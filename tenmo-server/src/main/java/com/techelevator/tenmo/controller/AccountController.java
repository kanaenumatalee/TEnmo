package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
//*kanae/jaron
@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {
    UserDao userDao;
    AccountDao accountDao;
    TransferDao transferDao;
    TransferStatusDao transferStatusDao;
    TransferTypeDao transferTypeDao;

    @RequestMapping(path = "/balance",method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
    return accountDao.getBalance(principal.getName());
    }


    //getUsers
    //addTransfer
    //getTransferTypeFromDescription
    //getTransferStatusByDescription
    //getAccountByUserId
    //getAccountFromAccountId
    //getTransfersByUserId
    //getTransferById
    //getUserByUserId
    //getAllTransfers
    //getTransferDescFromId
    //getTransferStatusFromId
    //getPendingTransfersByUserId
    //updateTransferStatus

}
