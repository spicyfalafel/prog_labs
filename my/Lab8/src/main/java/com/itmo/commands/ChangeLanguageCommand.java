package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;
import com.itmo.server.ServerMain;
import com.itmo.utils.LocaleClass;

import java.util.Arrays;

/*
    when user choose a different language in UI
    server also should change his language
    to give a proper responses
 */
public class ChangeLanguageCommand extends Command{


    public ChangeLanguageCommand(String[] args) {
        super(args);
        setNoRightsToExecute(true);
    }

    public ChangeLanguageCommand() {
        setNoRightsToExecute(true);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public String execute(Application application, User user) {
        boolean languageIsSupported = Arrays.stream(LocaleClass.SupportedLanguages.values()).map(Enum::toString)
                .anyMatch(args[0]::equals);
        if(languageIsSupported){
            ServerMain.localeClass.changeLocaleByTag(args[0]);
            return "";
        }
        return ServerMain.localeClass.getString("couldn't_change_language_at_server.text");
    }
}
