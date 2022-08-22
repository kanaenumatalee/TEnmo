package com.techelevator.tenmo.model;

import com.techelevator.tenmo.exception.InsufficientBalanceException;

import java.math.BigDecimal;

public class Account {
    private long account_id;
    private long user_id;
    private BigDecimal balance;

    public Account() {

    }

    public Account(long account_id, long user_id, BigDecimal balance) {
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }




    public void sendMoney(BigDecimal money) throws InsufficientBalanceException {
        BigDecimal newBalance = new BigDecimal(String.valueOf(balance.subtract(money)));
        if(newBalance.compareTo(BigDecimal.ZERO) >= 0) {
            this.balance = newBalance;
        } else {
            throw new InsufficientBalanceException();
        }
    }

    public void getMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(balance)).add(money);
    }





    @Override
    public String toString() {
        return "Account{" +
                "account_id=" + account_id +
                ", user_id=" + user_id +
                ", balance=" + balance +
                '}';
    }
}