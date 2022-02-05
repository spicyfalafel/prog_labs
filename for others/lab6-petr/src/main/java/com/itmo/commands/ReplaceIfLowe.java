package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.Product;
import com.itmo.product.User;


public class ReplaceIfLowe extends AbstractCommand{

    private IOManager manager;
    private String str1 = "Incorrect data in script";
    private String str2 = "Not found this key in collection";
    private User user;
    public ReplaceIfLowe(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.user = user;
        this.manager = manager;
    }

    @Override
    public String execute() {
        Integer key = manager.getKey();
        if (key != null) {
            if (products.findKey(key, user)) {
                Product product = manager.getProduct();
                if (product != null) {
                    products.replaceIfLowe(key, product, user);
                } else {
                   return str1;
                }
            } else {
                return str2;
            }
        }
        return "";
    }
}
