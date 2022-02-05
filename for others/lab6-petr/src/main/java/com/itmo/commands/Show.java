package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

public class Show extends AbstractCommand{
    public Show(String name, HashTableManager products, StringManager stringManager) {
        super(name, products, stringManager);
    }

    @Override
    public String execute() {
        return products.toStr();
    }
}
