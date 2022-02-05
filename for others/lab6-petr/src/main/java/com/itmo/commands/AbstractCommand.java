package com.itmo.commands;

import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

/**
 * Абстрактная команда
 */
public abstract class AbstractCommand implements Command{
    protected final String name;
    protected HashTableManager products;
    protected StringManager stringManager;
    protected boolean exit;

    public AbstractCommand(String name, HashTableManager products, StringManager stringManager) {
        this.name = name;
        this.products = products;
        this.stringManager = stringManager;
        exit = false;
    }

    @Override
    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
