package com.itmo.server;

import com.itmo.commands.*;
import com.itmo.exceptions.CommandNotFoundException;
import com.itmo.exceptions.RecursiveScript;
import com.itmo.managers.*;
import com.itmo.product.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.TreeSet;


public class ExecuteCommand {

    private BufferedReader in;
    private final String path;
    private final HashTableManager products;
    private final ConsoleManager consoleManager;
    private final StringManager stringManager;
    private final DatabaseManager db;

    public ExecuteCommand(PrintWriter printWriter, BufferedReader in, User user, HashTableManager products, DatabaseManager db) {
        this.stringManager = new StringManager(printWriter);
        this.in = in;
        this.products = products;
        this.db = db;
        path = System.getenv().get("FILE6");
        consoleManager = new ConsoleManager(products, stringManager, in);
    }

    private AbstractCommand findCommand(String command, HashTableManager products, IOManager manager,
                                        StringManager stringManager, User user) throws CommandNotFoundException {
        switch (command) {
            case "help":
                return new Help(command, products, stringManager);
            case "login":
                return new LoginUser(command, products, manager, stringManager, user);
            case "register":
                return new RegisterUser(command, products, manager, stringManager);
            case "info":
                return new Info(command, products, stringManager);
            case "show":
                return new Show(command, products, stringManager);
            case "insert":
                return new Insert(command, products, manager, stringManager, user);
            case "update":
                return new Update(command, products, manager, stringManager, user);
            case "remove_key":
                return new RemoveKey(command, products, manager, stringManager, user);
            case "clear":
                return new Clear(command, products, stringManager, user);
            case "exit":
                return new Exit(command, products, stringManager, user);
            case "execute_script":
                return new ExecuteScript(command, products, manager.getName(), manager, stringManager, this, user);
            case "replace_if_lowe":
                return new ReplaceIfLowe(command, products, manager, stringManager, user);
            case "remove_greater_key":
                return new RemoveGreaterKey(command, products, manager, stringManager, user);
            case "remove_lower_key":
                return new RemoveLowerKey(command, products, manager, stringManager, user);
            case "sum_of_price":
                return new SumOfPrice(command, products, stringManager);
            case "min_by_creation_date":
                return new MinByCreationDate(command, products, stringManager);
            case "count_by_owner":
                return new CountByOwner(command, products, manager, stringManager);
            default:
                throw new CommandNotFoundException("Command \"" + command + "\" doesn't exist");
        }
    }

    public boolean doCommand(String command, User user) {

        boolean exit = false;
        try {
            AbstractCommand com = findCommand(command, products, consoleManager, stringManager, user);
            System.out.println(com);
            if (!user.getName().equals("unregistered")
                    || com instanceof LoginUser || com instanceof RegisterUser || com instanceof Help) {
                String ans = com.execute();
                Thread t = new Thread(new SendResponseThread(stringManager, ans));
                t.start();
                t.join();
                System.out.println("command executed");
            } else {
                stringManager.multiLine("Login please");
            }

            if (com.isExit() || consoleManager.isExit()) {
                exit = true;
                System.out.println("command executed");
                stringManager.multiLine("Command executed");
            }

        } catch (CommandNotFoundException | InterruptedException e) {
            stringManager.multiLine(e.getMessage());
        }

        return exit;
    }

}
