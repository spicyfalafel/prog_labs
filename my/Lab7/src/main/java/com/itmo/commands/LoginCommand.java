package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.utils.FieldsScanner;
import com.itmo.utils.PassEncoder;

public class LoginCommand extends Command{
    private String login = null;
    private String pass = null;
    private final String  PEPPER = "1@#$&^%$)3";

    @Override
    public void clientInsertion() {
        login = FieldsScanner.getInstance().scanStringNotEmpty("логин").trim();
        pass = FieldsScanner.getInstance().scanLine("пароль (нет пароля - Enter)").trim();
    }

    @Override
    public String execute(Application application, User user) {
        User u = null;
        if(!application.activeUsers.containsUserName(login)){
            String hashPassword = new PassEncoder().getHash(pass, null);
            u = new User(login, hashPassword);
            if(application.manager.containsUser(u)){
                application.activeUsers.removeUser(user);
                user.setName(login);
                user.setHashPass(hashPassword);
                application.activeUsers.addUser(user);
                return "Здравствуйте, " + user.getName();
            }else{
                return "Пользователь с такими логином и паролем не зарегистрирован.";
            }
        }else{
            return "Такой пользователь уже на сервере.";
        }
    }

    @Override
    public String getDescription() {
        return "аутенфикация пользователя, требуется для доступа к командам";
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }
}
