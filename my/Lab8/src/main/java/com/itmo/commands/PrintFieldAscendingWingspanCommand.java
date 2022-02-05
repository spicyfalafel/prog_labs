package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;

public class PrintFieldAscendingWingspanCommand extends Command {

    public PrintFieldAscendingWingspanCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public String execute(Application application, User user) {
        return application.getCollection().printFieldAscendingWingspan();
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("print_wingspan_ordered_by.text");
    }
}
