package com.itmo.commands;

import com.itmo.managers.DatabaseManager;
import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;

public class LoginUser extends AbstractCommand {

    private IOManager manager;
    private User user;
    public LoginUser(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.manager = manager;
        this.user = user;
    }

    @Override
    public String execute() {
        User u = manager.getUser();
        DatabaseManager db = products.getDb();
        if (db.containsUser(u)) {
            user.setName(u.getName());
            return "You're logged in";
        } else {
            return "Error. Check login/password";
        }
    }
}
