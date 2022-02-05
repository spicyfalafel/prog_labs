package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;

public class ShowCommand extends Command {

    public ShowCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    public String getDescription() {
        return UIApp.localeClass.getString("print_all_elements.text");
    }

    @Override
    public String execute(Application application, User user) {

        return application.getCollection().show();
    }

}
