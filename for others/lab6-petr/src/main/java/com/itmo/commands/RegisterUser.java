package com.itmo.commands;

import com.itmo.managers.DatabaseManager;
import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;

public class RegisterUser extends AbstractCommand{

    private IOManager manager;
    public RegisterUser(String name, HashTableManager products, IOManager manager, StringManager stringManager) {
        super(name, products, stringManager);
        this.manager = manager;
    }

    @Override
    public String execute() {
        User u = manager.getUser();
        DatabaseManager db = products.getDb();
        db.addUser(u);
        return "registered, please login";
    }
}
