package com.itmo.client;

import com.itmo.app.UIApp;
import com.itmo.app.controllers.AuthorizationController;

/*
    Класс для проверки аргументов программы и её запуска
    в графическом режиме
 */
public class MainUI {
    public static String host;
    public static int port;
    public static void main(String[] args) {
        String[] hostAndPort = checkArgs(args);
        host = hostAndPort[0];
        port = Integer.parseInt(hostAndPort[1]);
        Client client = new Client(host, port);
        client.connect();
        UIApp.setClient(client);
        UIApp ui = new UIApp();
        ui.run(args);
    }

    public static String[] checkArgs(String[] args){
        if(args.length==0){
            return new String[]{"localhost", "8080"};
        }
        if(args.length!=2){
            System.out.println("Требуются 2 аргумента: хост и порт");
            System.exit(1);
        }else {
            try {
                Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Не целое число, использую 8080");
                args[1]="8080";
            }
        }
        return args;
    }
}
