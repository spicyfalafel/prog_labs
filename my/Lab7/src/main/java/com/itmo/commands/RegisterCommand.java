package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.client.User;
import com.itmo.server.ReadRequestThread;
import com.itmo.utils.FieldsScanner;
import com.itmo.utils.PassEncoder;
import com.itmo.utils.SimplePasswordGenerator;

public class RegisterCommand extends Command {
    private final String PEPPER = "1@#$&^%$)3";
    private String login = null;
    private String pass = null;
    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    @Override
    public void clientInsertion() {
        login = FieldsScanner.getInstance().scanStringNotEmpty("логин для регистрации");
        System.out.println("Ваш будущий логин: " + login + ". Нужен ли вам пароль?");
        pass = registerPassword();
    }

    @Override
    public String execute(Application application, User user) {
        if(!application.manager.containsUserName(login)){
            pass = new PassEncoder().getHash(pass, null);
            user.setName(login);
            user.setHashPass(pass);
            application.manager.insertUser(user);
            return "Регистрация успешна. Ваш логин: " + user.getName();
        }else return "Пользователь с таким логином уже зарегистрирован.";
    }


    private String registerPassword() {
        FieldsScanner fs = FieldsScanner.getInstance();
        boolean yes = fs.scanYN();
        if(yes){
            String passw = fs.scanStringNotEmpty("пароль или введите generate для его авто-генерации");
            passw = passw.trim().equals("generate") ?
                    new SimplePasswordGenerator(true, true, true, false ).generate(10,10)
                    : passw;
            System.out.println("Ваш будущий пароль: " + passw);
            return passw;
        }else{
            return "";
        }
    }
}
