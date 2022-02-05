package com.itmo.exceptions;

/**
 * Исключение неправильных входных данных
 */
public class InvalidArgument extends Exception {
    public InvalidArgument(String msg) {
        super(msg);
    }
}
