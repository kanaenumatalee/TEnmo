package com.techelevator.exception;

public class NotEnoughBalanceException extends Exception{
    public NotEnoughBalanceException() {
        super("You don't have enough balance for the transaction.");
    }
}
