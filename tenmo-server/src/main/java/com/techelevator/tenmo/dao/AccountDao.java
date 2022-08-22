package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

//*kanae/jaron
public interface AccountDao {
    BigDecimal getBalance(String user);
    int findIdByAccountId(long accountId);

    Account getAccountByUserId(long userId);
    Account getAccountByAccountId(long accountId);

    void updateAccount(Account account);

}
