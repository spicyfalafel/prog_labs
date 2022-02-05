package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.Product;
import com.itmo.product.User;


public class Insert extends AbstractCommand{

    private IOManager manager;
    private String string = "Incorrect data in script";
    private User user;
    public Insert(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.user = user;
        this.manager = manager;
    }

    @Override
    public String execute() {
        Integer key = manager.getKey();
        Product product = manager.getProduct();
        product.setUserOwner(user);
        System.out.println("22");
        if (product != null || key != null) {
            System.out.println("24");
            products.insertToDb(key, product);
        } else {
            System.out.println("28");
            return string;
        }
        return "Added";
    }
}
