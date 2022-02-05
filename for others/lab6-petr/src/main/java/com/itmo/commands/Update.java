package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.Product;
import com.itmo.product.User;


public class Update extends AbstractCommand{
    private IOManager manager;
    private String string = "Id not found or it's property of someone else";
    private User user;
    public Update(String name, HashTableManager products, IOManager manager, StringManager stringManager, User user) {
        super(name, products, stringManager);
        this.manager = manager;
        this.user = user;
    }

    @Override
    public String execute() {
        Integer id = manager.getId();
        if (id != null) {
            if (products.findId(id, user)) {
                Product product = manager.getProduct();
                if (product != null) {
                    product.setId(id);
                    products.insertToDb(products.removeById(id, user), product);
                }
            } else {
                return string;
            }
        }
        return "";
    }
}
