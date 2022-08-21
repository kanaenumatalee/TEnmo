package com.techelevator.exception;

public class InvalidUserException extends Exception{
    public InvalidUserException() {
        super("You cannot send money to yourself.");
    }
}
