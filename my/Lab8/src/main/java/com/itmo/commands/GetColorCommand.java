package com.itmo.commands;

import com.itmo.client.User;
import com.itmo.server.Application;

import java.util.Arrays;

public class GetColorCommand extends Command {
    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    //god im sorry
    @Override
    public String execute(Application application, User user) {
        double[] res = application.db.getColorOfUser(user.getName());
        return "" + res[0] + " " + res[1] + " " + res[2];
    }
}
