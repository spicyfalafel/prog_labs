package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;

/**
 * The type Show command.
 */
public class ShowCommand extends Command {


    /**
     * Instantiates a new Command.
     */
    public ShowCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    public String getDescription() {
        return "вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public String execute(Application application, User user) {
        return application.getCollection().show();
    }

}
