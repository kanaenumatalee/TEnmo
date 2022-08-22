package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account/")
public class AccountController {

    @Autowired
    UserDao userDao;
    @Autowired
    AccountDao accountDao;


    @RequestMapping(value = "balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        return accountDao.getBalance(principal.getName());
    }

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userDao.findAll();
    }


    // findIdByAccountId
    @RequestMapping(value = "findIdByAccountId/{id}", method = RequestMethod.GET)
    public int findIdByAccountId(@PathVariable int userId){
        return accountDao.findIdByAccountId(userId);
    }

    // getAccountByUserId
    @RequestMapping(value = "getAccountByUserId/{id}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId){
        return accountDao.getAccountByUserId(userId);
    }



    // getAccountByAccountId
    @RequestMapping(value = "getAccountByAccountId/{id}",method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int accountId){
        return accountDao.getAccountByAccountId(accountId);
    }

    // updateAccount
    @RequestMapping(value ="updateAccount/{id}",method = RequestMethod.GET)
    public void updateAccount(@RequestBody Account account){
        accountDao.updateAccount(account);
    }



    //findIdByUsername

}