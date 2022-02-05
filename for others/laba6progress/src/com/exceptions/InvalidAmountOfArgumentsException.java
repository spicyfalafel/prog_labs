package com.exceptions;

import java.security.PrivilegedExceptionAction;

public class InvalidAmountOfArgumentsException extends Exception {
    private int requiredAmount;
    public InvalidAmountOfArgumentsException(int requiredAmount) {
        super("Expected " + requiredAmount + " arguments");
        this.requiredAmount = requiredAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public void printMessage() {
        System.out.println("Error. Expected " + getRequiredAmount() + " arguments");
    }
}
