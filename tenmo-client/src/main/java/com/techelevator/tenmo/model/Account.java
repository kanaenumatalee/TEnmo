package com.techelevator.tenmo.model;

import java.math.BigDecimal;
//*kanae/jaron
public class Account {

    private final BigDecimal STARTING_BALANCE=BigDecimal.valueOf(1000);
    private long userId;
    private long accountId;
    private BigDecimal balance;
    public  Account(){}

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
    public BigDecimal getBalance(){
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
