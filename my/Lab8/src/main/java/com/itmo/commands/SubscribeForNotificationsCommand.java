package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;

public class SubscribeForNotificationsCommand extends Command {
    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String execute(Application application, User user) {
        return "";
    }
}
