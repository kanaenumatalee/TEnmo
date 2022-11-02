package com.techelevator.exception;


public class NegativeValueException extends Exception {
    public NegativeValueException() {
        super("Input must be at least $1.");
    }
}
