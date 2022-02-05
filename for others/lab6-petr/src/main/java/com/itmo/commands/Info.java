package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;


public class Info extends AbstractCommand {
    private String string = "Type: Product\n" + "Initialization date: " + products.getDate() + "\nSize: " + products.size();
    public Info(String name, HashTableManager products, StringManager stringManager) {
        super(name, products, stringManager);
    }

    @Override
    public String execute() {
        return string;
    }
}
