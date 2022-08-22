package com.techelevator.tenmo.model;

import java.math.BigDecimal;
//*kanae/jaron
public class Account {
    private int userId;

    private int accountId;

    private BigDecimal balance;

    public BigDecimal getBalance(){
        return balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Account) {
            Account otherAccount = (Account) other;
            return otherAccount.getBalance().equals(balance)
                    && otherAccount.getUserId()==userId;
        } else {
            return false;
        }
    }


}
