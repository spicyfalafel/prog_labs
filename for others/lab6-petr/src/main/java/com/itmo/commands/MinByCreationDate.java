package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

public class MinByCreationDate extends AbstractCommand{
    public MinByCreationDate(String name, HashTableManager products, StringManager stringManager) {
        super(name, products, stringManager);
    }

    @Override
    public String execute() {
        return products.minByCreationDate().toString();
    }
}
