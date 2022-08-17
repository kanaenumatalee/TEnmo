package com.techelevator.tenmo.model;

import java.math.BigDecimal;
//*kanae/jaron
public class Account {
    private long userId;

    private long accountId;

    private BigDecimal balance;

    public BigDecimal getBalance(){
        return balance;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
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
