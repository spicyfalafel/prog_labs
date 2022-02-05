package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;
import com.itmo.server.ServerMain;
import com.itmo.utils.FieldsScanner;
import com.itmo.utils.PassEncoder;
import com.itmo.utils.SimplePasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.Setter;

public class RegisterCommand extends Command {

    public RegisterCommand() {
        setNoRightsToExecute(true);
    }

    public RegisterCommand(String login, String pass){
        setNoRightsToExecute(true);
        this.login = login;
        this.pass = pass;
    }

    @Setter
    private String login = null;
    @Setter
    private String pass = null;
    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void clientInsertionFromConsole() {
        login = FieldsScanner.getInstance().scanStringNotEmpty(
                ServerMain.localeClass.getString("login_for_registration.text")
        );
        System.out.println(
                ServerMain.localeClass.getString("your_login.text") + login + ". " +
                        ServerMain.localeClass.getString("do_you_need_password.text"));
        pass = registerPassword();
    }

    @Override
    public String execute(Application application, User user) {
        if(!application.db.containsUserName(login)){
            pass = new PassEncoder().getHash(pass, null);
            user.setName(login);
            user.setHashPass(pass);
            application.db.insertUser(user);
            user.setRandomColor();
            application.db.setUserColor(user.getName(), user.getColor());
            return ServerMain.localeClass.getString("registration_is_completed.text")
            + ServerMain.localeClass.getString("your_login.text") + ": " + user.getName();
        }else return ServerMain.localeClass.getString("this_user_already_exists.text");
    }


    private String registerPassword() {
        FieldsScanner fs = FieldsScanner.getInstance();
        boolean yes = fs.scanYN();
        if(yes){
            String passw = fs.scanStringNotEmpty(
                    ServerMain.localeClass.getString("password.text") + " " +
                            ServerMain.localeClass.getString("or_write_generate_for_autogeneration.text")
            );
            passw = passw.trim().equals("generate") ?
                    new SimplePasswordGenerator(true, true, true, false ).generate(10,10)
                    : passw;
            System.out.println(ServerMain.localeClass.getString("your_password.text")
                    +": "+ passw);
            return passw;
        }else{
            return "";
        }
    }
}
