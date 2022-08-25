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

    @RequestMapping(path="/users/user/{id}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable long id) {
        return userDao.getUserByUserId(id);
    }

    /* findIdByAccountId
    @RequestMapping(value = "users/{accountId}", method = RequestMethod.GET)
    public int findIdByAccountId(@PathVariable int accountId){
        return accountDao.findIdByAccountId(accountId);
    }*/

    // getAccountByUserId
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public Account getAccountByUserId(@PathVariable int userId){
        return accountDao.getAccountByUserId(userId);
    }



    // getAccountByAccountId
    @RequestMapping(value = "users/{accountId}",method = RequestMethod.GET)
    public Account getAccountByAccountId(@PathVariable int accountId){
        return accountDao.getAccountByAccountId(accountId);
    }

    // updateAccount
    @RequestMapping(value ="users/{id}",method = RequestMethod.PUT)
    public void updateAccount(@RequestBody Account account, @PathVariable int accountId){
        accountDao.updateAccount(account);
    }



    /*
    //findIdByUsername
    @RequestMapping(value = "users/{username}", method = RequestMethod.GET)
    public int findIdByUsername(@PathVariable String username){
        return userDao.findIdByUsername(username);
    }
    */

}