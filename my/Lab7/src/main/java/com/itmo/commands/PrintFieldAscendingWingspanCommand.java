package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;

/**
 * The type Print field ascending wingspan command.
 */
public class PrintFieldAscendingWingspanCommand extends Command {

    /**
     * Instantiates a new Command.
     *
     */
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
        return "вывести значения поля wingspan в порядке возрастания";
    }
}
