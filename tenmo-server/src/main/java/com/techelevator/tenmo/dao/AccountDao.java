package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

//*kanae/jaron
public interface AccountDao {
    BigDecimal getBalance(String user);
    int findIdByAccountId(int accountId);

    Account getAccountByUserId(int userId);
    Account getAccountByAccountId(int accountId);

    void updateAccount(Account account);

}
