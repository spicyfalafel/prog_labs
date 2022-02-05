package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import com.itmo.server.ServerMain;
import com.itmo.utils.FieldsScanner;
import com.itmo.utils.PassEncoder;
import lombok.Getter;
import lombok.Setter;


public class LoginCommand extends Command{
    @Getter @Setter
    private String login = null;
    @Getter @Setter
    private String pass = null;

    public LoginCommand(String login, String pass){
        setNoRightsToExecute(true);
        this.login = login;
        this.pass = pass;
    }
    public LoginCommand() {
        setNoRightsToExecute(true);
    }

    @Override
    public void clientInsertionFromConsole() {
        login = FieldsScanner.getInstance().scanStringNotEmpty(
                ServerMain.localeClass.getString("login.text")
        ).trim();
        pass = FieldsScanner.getInstance().scanLine(
                ServerMain.localeClass.getString("password.text")
                + ", " + ServerMain.localeClass.getString("enter_else.text")
        ).trim();
    }

    @Override
    public String execute(Application application, User user) {
        String hashPassword = new PassEncoder().getHash(pass, null);
        User u = new User(login, hashPassword);
        if(application.db.containsUser(u)){
            if(!application.activeUsers.containsUserName(login) || user.getName().equals(login)){
                logUser(application, user, hashPassword);
                return  ServerMain.localeClass.getString("hello.text") + ", " + user.getName();
            }else{
                return ServerMain.localeClass.getString("already_on_server.text");
            }
        }else{
            return ServerMain.localeClass.getString("not_registered.text");
        }
    }

    private void logUser(Application application, User user, String hashPassword){
        application.activeUsers.removeUserByName(user.getName());
        user.setName(login);
        user.setHashPass(hashPassword);
        user.setColor(application.db.getColorOfUser(login));
        application.activeUsers.addUserName(user.getName());
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("login_description.text");
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }
}