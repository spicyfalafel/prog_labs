package com.itmo.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NotYourPropertyException extends Throwable {
    private String owner;

    @Override
    public String getMessage() {
        return owner;
    }
}
