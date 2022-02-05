package com.itmo.exceptions;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String badCommand){
        System.out.println("Такой команды \""+badCommand+"\" нет.");
    }
}
