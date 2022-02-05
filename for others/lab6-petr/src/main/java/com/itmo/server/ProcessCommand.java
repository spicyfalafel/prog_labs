package com.itmo.server;

import com.itmo.managers.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 2.	Для многопотчной обработки полученного запроса использовать Fixed thread pool
public class ProcessCommand {

    private BufferedReader in;
    private final HashTableManager products;
    private final ConsoleManager consoleManager;
    private final StringManager stringManager;


    public ProcessCommand(PrintWriter out, BufferedReader in){
        this.stringManager = new StringManager(out);
        this.in = in;

        DatabaseManager db = new DatabaseManager();
        products = db.getCollectionFromDatabase();
        consoleManager = new ConsoleManager(products, stringManager, in);

    }
}
