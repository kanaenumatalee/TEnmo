package com.techelevator.exception;


public class NoUserFoundException extends Exception{
    public NoUserFoundException() {
        super("I'm sorry, no valid user was found.");
    }
}
