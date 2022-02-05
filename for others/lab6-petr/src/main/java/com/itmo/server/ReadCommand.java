package com.itmo.server;

import com.itmo.managers.ConsoleManager;
import com.itmo.managers.DatabaseManager;
import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 1.	Для многопоточного чтения запросов использовать Fixed thread pool
// читает команду, проверяет на правильность, отдает объект команды
public class ReadCommand {

    private BufferedReader in;
    private final HashTableManager products;
    private final ConsoleManager consoleManager;
    private final StringManager stringManager;


    public ReadCommand(PrintWriter out, BufferedReader in){
        this.stringManager = new StringManager(out);
        this.in = in;

        DatabaseManager db = new DatabaseManager();
        products = db.getCollectionFromDatabase();
        consoleManager = new ConsoleManager(products, stringManager, in);

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        //threadPool.execute();
    }

}
