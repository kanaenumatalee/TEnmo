package com.techelevator.exception;

public class InvalidTransferIdException extends Exception{
    public InvalidTransferIdException(){
        super("Invalid Transfer ID. Please choose different ID.");
    }
}
