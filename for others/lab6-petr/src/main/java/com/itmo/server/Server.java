package com.itmo.server;

import com.itmo.managers.DatabaseManager;
import com.itmo.managers.HashTableManager;
import com.itmo.managers.StringManager;
import com.itmo.product.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);


        DatabaseManager db = new DatabaseManager();
        HashTableManager products = db.getCollectionFromDatabase();

        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        System.out.println("Start of server");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted");
            threadPool.execute(() -> {
                try {

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    StringManager stringManager = new StringManager(out);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    User user = new User("unregistered", "");
                    ExecuteCommand executeCommand = new ExecuteCommand(out, in, user, products, db);
                    boolean notExit = true;
                    while (notExit) {
                        String inputLine = in.readLine();
                        System.out.println(inputLine);
                        if (inputLine == null) {
                            break;
                        }
                        if (executeCommand.doCommand(inputLine, user)) {
                            stringManager.multiLine(user.getName() + ": end of session");
                            serverSocket.close();
                            notExit = false;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}

