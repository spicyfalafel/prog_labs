package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;


public class RemoveKey extends AbstractCommand{
    private IOManager manager;
    private String string = "Not found this key in collection, might be not your property";
    private User user;
    public RemoveKey(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.user = user;
        this.manager = manager;
    }

    @Override
    public String execute() {
        Integer key = manager.getKey();
        if (key != null){
            if (!products.removeByKey(key, user)) {
                return string;
            }
        }
        return "";
    }
}
