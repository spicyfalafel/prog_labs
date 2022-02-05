package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class HelpCommand extends Command {

    public HelpCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void clientInsertionFromConsole() {
        System.out.println(CommandsInvoker.getInstance().getHelp());
    }

    @Override
    public String execute(Application application, User user) {
        return "";
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("print_help_with_commands.text");
    }
}
