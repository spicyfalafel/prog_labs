package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

public class SumOfPrice extends AbstractCommand{
    public SumOfPrice(String name, HashTableManager products, StringManager stringManager) {
        super(name, products, stringManager);
    }

    @Override
    public String execute() {
        return products.sumOfPrice().toString();
    }
}
