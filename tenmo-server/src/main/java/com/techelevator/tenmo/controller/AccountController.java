package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
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
@RequestMapping("/account")
public class AccountController {
    //auto wire annotation provides more fine-grained control over where and how it should be accomplished
    //allows
    @Autowired
    AccountDao accountDao ;

    @RequestMapping(path = "/balance",method = RequestMethod.GET)//brake boint on line 18/19
    public BigDecimal getBalance(Principal principal){
        System.out.println("In Controller.");
//        System.out.println(principal);
        System.out.println(principal.getName());

        return accountDao.getBalance(principal.getName());
//        return null;
    }
// needs request mapping/ try something else with principle like user object /write another method for endpoint base account


}