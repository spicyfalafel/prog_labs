package com.itmo.commands;


import com.itmo.app.Application;
import com.itmo.client.User;

/**
 * The type Info command.
 */
public class InfoCommand extends Command {
    /**
     * Instantiates a new Command.
     *
     */
    public InfoCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }




    @Override
    public String getDescription() {
        return "вывести в стандартный поток вывода информацию о коллекции " +
                "(тип, дата инициализации, количество элементов и т.д.)";
    }

    @Override
    public String execute(Application application, User user) {
            return application.getCollection().getCollectionInfo();
    }
}
