package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;


public class Clear extends AbstractCommand{
    private String string = "Collection is empty";
    private User user;
    public Clear(String name, HashTableManager products, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.user = user;
    }

    @Override
    public String execute() {
        products.clear(user);
        return string;
    }
}
