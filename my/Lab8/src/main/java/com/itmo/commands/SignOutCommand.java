package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;

/*
    I don't want to confuse sign out from account and exit from app.
 */
public class SignOutCommand extends Command{


    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String execute(Application application, User user) {
        System.out.println("USER IN EXECUTE:" + user);
        application.activeUsers.removeUserByName(user.getName());
        return "";
    }
}
