package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.collection.Dragon;
import com.itmo.utils.FieldsScanner;

/**
 * The type Remove lower element command.
 */
public class RemoveLowerThanElementCommand extends Command {

    /**
     * Instantiates a new Command.
     *
     */
    public RemoveLowerThanElementCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    private Dragon dr;
    @Override
    public void clientInsertion() {
        FieldsScanner helper = FieldsScanner.getInstance();
        dr = helper.scanDragon();
    }

    @Override
    public String execute(Application application, User user) {
        return application.getCollection().removeLower(dr, user);
    }

    @Override
    public String getDescription() {
        return "удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
