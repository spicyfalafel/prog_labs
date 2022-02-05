package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;

public class RemoveLowerKey extends AbstractCommand{
    private IOManager manager;
    private User user;
    public RemoveLowerKey(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.manager = manager;
        this.user = user;
    }

    @Override
    public String execute() {
        Integer key = manager.getKey();
        if (key != null) {
            products.removeLowerKey(key, user);
        }
        return "";
    }
}
