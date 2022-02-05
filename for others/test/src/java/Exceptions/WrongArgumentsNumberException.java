package Exceptions;

import Commands.Command;

public class WrongArgumentsNumberException extends Exception {
    public WrongArgumentsNumberException(int required, int found){
        System.out.println("Wrong argumentsNumberException: " + found);
        System.out.println("Expected: " + required);
    }
}
