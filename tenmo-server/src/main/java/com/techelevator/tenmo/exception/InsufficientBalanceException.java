package com.techelevator.tenmo.exception;


public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException() {
        super("Not enough balance in your account.");
    }
}
