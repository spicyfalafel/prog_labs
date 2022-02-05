package com.itmo.commands;

import com.itmo.exceptions.RecursiveScript;
import com.itmo.managers.FileManager;
import com.itmo.managers.HashTableManager;
import com.itmo.managers.IOManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;
import com.itmo.server.ExecuteCommand;

import java.io.FileNotFoundException;


public class ExecuteScript extends AbstractCommand{
    private String path;
    private IOManager manager;
    private ExecuteCommand executeCommand;
    private String string = "File not found";
    private User user;
    public ExecuteScript(String name, HashTableManager products, String path, IOManager manager, StringManager stringManager,
                         ExecuteCommand executeCommand, User user) {
        super(name, products, stringManager);
        this.user = user;
        this.path = path;
        this.manager = manager;
        this.executeCommand = executeCommand;
    }

    @Override
    public String execute() {
        try {
            FileManager fileManager = new FileManager(products, path, true, manager.getHistory(), stringManager, executeCommand);
            fileManager.readData(user);
            if (fileManager.isExit()) {
                exit = true;
            }
        } catch (FileNotFoundException | NullPointerException e) {

        } catch (RecursiveScript recursiveScript) {
            return recursiveScript.getMessage();
        }
        return "";
    }
}
