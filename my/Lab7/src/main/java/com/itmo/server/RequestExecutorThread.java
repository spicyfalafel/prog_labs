package com.itmo.server;

import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.commands.Command;
import com.itmo.commands.ExitCommand;
import com.itmo.commands.LoginCommand;
import com.itmo.commands.RegisterCommand;
import lombok.AllArgsConstructor;
import java.nio.channels.SocketChannel;
@AllArgsConstructor
public class RequestExecutorThread extends Thread {
    private Command command;
    private SocketChannel channel;
    private Application application;
    private User client;

    @Override
    public void run() {
        if (command != null) {
            String res = "Вы не авторизованы. Команды недоступны";
            if(command instanceof RegisterCommand
                    || command instanceof LoginCommand){
                res = command.execute(application, client);
            }else if(command instanceof ExitCommand && client.getName().equals("unregistered")){
                res = "Вообще у вас прав нет, но хорошо, выходите.\n" + command.execute(application, client);
            }else{
                if(!client.getName().equals("unregistered") && application.activeUsers.containsUserName(client.getName())){
                    res = command.execute(application, client);
                }
            }
            ServerWithThreads.executorService.execute(new GiveResponseTask(channel, application, client, res));
        }
    }
}

