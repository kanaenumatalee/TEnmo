package com.techelevator.exception;

public class NoUserFoundException extends Exception{
    public NoUserFoundException() {
        super("No valid user was found.");
    }
}
