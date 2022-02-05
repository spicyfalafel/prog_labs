package com.itmo.client;

import com.itmo.commands.Command;
import com.itmo.commands.ExitCommand;
import com.itmo.server.Response;
import com.itmo.utils.SerializationManager;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
    Этот класс нужен для того, чтобы подключиться к серверу, иметь возможность послать команду
    и получить ее обратно.
    Также можно запустить клиента в консольном режиме прям отсюда.
 */
public class Client {
    @Getter
    private Socket socket;
    private static final Scanner scanner = new Scanner(System.in);

    public final int BUFFER_SIZE = 4096;
    @Getter @Setter
    private User user;
    private boolean notExit = true;
    private final int port;
    private boolean once = true;
    private final String host;
    private MyConsole console;


    // консольный режим запускается прям отсюда
    public void runConsoleMode() {
        console = new MyConsole();
        connect();
        while (notExit) {
            Command command = getClientCommandByConsole();
            sendCommandToServer(command);
            String answer = getAnswerFromServer();
            setUserNameAfterRegistering(answer);
            checkExitCommand(command);
        }
        closeEverything();
    }

    public void connect()  {
        System.out.println("Пытаюсь установить соединение с сервером (" + host + ":" + port + ")");
        InetAddress addr;
        while (true) {
            try {
                addr = InetAddress.getByName(host);
                socket = new Socket(addr, port);
                System.out.println("Подключено: " + socket);
                once=true;
                return;
            } catch (UnknownHostException e) {
                if(once){
                    once=false;
                    System.out.println("Неправильно указан хост");
                }
            } catch (IOException e) {
                if(once){
                    once=false;
                    System.out.println("Сервер отключен");
                }
            }
        }
    }

    public Client(String host, int port){
        this.host = host;
        this.port = port;
        user = new User("unregistered", "");
    }

    public void sendCommandToServer(Command command){
        try{
            command.setUser(user);
            sendCom(command);
        } catch (IOException e) {
            if (notExit) {
                System.out.println("Потеря соединения");
                connect();
            }
        }
    }

    private void setUserNameAfterRegistering(String answer){
        //todo
        if(answer.startsWith("Здравствуйте, ")) {
            user.setName(answer.trim().substring(14));
        }
    }

    private void checkExitCommand(Command command){
        if(command instanceof ExitCommand) {
            notExit = false;
            System.exit(0);
        }
    }


    private void sendCom(Command command) throws IOException {
        sendOneByte();
        sendComBySocket(command);
    }

    public String getAnswerFromServer(){
        try {
            return getAns();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String getAns() throws IOException, ClassNotFoundException {
        byte[] buff = new byte[BUFFER_SIZE];
        int got = socket.getInputStream().read(buff);
        if(got>0){
            Response r = SerializationManager.readObject(buff);
            setUserNameAfterRegistering(r.getAnswer()); // это костыль
            return r.getAnswer();
        }
        return null;
    }

    public byte[] getBytes() {
        byte[] res = new byte[BUFFER_SIZE];
        int got = 0;
        try {
            got = socket.getInputStream().read(res);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (got>0) return res;
        else return null;
    }

    public void closeEverything(){
        scanner.close();
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private void sendOneByte() throws IOException {
        byte[] b = new byte[1];
        b[0] = (byte) 127;
        socket.getOutputStream().write(b);
    }

    private void sendComBySocket(Command command) throws IOException {
        byte[] serializedCommand = SerializationManager.writeObject(command);
        sendBytes(serializedCommand);
    }

    public void sendBytes(byte[] bytes) throws IOException {
        socket.getOutputStream().write(bytes);
    }

    public Command getClientCommandByConsole(){
        Command command = console.getFullCommandFromConsole();
        command.setUser(user);
        return command;
    }
}