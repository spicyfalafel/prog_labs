package com.itmo.server;


import java.io.*;

public class ServerMain {
    public ServerMain() {
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = new Server();
        server.start(6666);
    }
}
