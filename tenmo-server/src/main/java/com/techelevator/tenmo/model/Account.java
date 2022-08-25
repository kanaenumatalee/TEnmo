package com.techelevator.tenmo.model;

import com.techelevator.tenmo.exception.InsufficientBalanceException;

import java.math.BigDecimal;

public class Account {
    private int accountId;
    private int userId;
    private BigDecimal balance;

    public Account() {

    }

    public Account(int account_id, int user_id, BigDecimal balance) {
        this.accountId = account_id;
        this.userId = user_id;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int account_id) {
        this.accountId = account_id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
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
                "account_id=" + accountId +
                ", user_id=" + userId +
                ", balance=" + balance +
                '}';
    }
}