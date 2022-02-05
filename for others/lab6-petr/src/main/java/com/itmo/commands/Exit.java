package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;


public class Exit extends AbstractCommand{
    private String string = "The end of session: ";
    private User user;
    public Exit(String name, HashTableManager products, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.user = user;
    }

    @Override
    public String execute() {
        setExit(true);
        return string + user.getName();
    }
}
