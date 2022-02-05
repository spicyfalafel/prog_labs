package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.Person;

public class CountByOwner extends AbstractCommand{
    private IOManager manager;

    public CountByOwner(String name, HashTableManager products, IOManager manager, StringManager stringManager) {
        super(name, products, stringManager);
        this.manager = manager;
    }

    @Override
    public String execute() {
        Person owner = manager.getOwner();
        if (owner != null) {
            return "" + products.countByOwner(owner);
        }
        return "person in null";
    }
}
