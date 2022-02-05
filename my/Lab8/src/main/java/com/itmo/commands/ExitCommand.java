package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import lombok.Setter;

import java.nio.channels.SocketChannel;

public class ExitCommand extends Command {

    public ExitCommand(String[] args) {
        super(args);
        setNoRightsToExecute(true);
    }

    public ExitCommand() {
        setNoRightsToExecute(true);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("exit_program.text");
    }

    @Override
    public String execute(Application application, User user) {
        application.activeUsers.removeUserByName(user.getName());
        return "byebye";
    }
}