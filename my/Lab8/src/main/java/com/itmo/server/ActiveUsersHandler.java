package com.itmo.server;

import com.itmo.client.User;
import lombok.Getter;

import java.util.HashSet;

public class ActiveUsersHandler {


    //private HashSet<String> activeUsers = new HashSet<>();
    private static ActiveUsersHandler handler=null;

    private HashSet<User> users = new HashSet<>();



    public static ActiveUsersHandler getInstance(){
        if(handler==null) return new ActiveUsersHandler();
        else return handler;
    }
    private ActiveUsersHandler(){
    }


    public boolean containsUserName(String name) {
        return users.stream().anyMatch(user -> user.getName().equals(name));
    }

    //добавляем пользователя в активные
    public void addUserName(String username) {
        User newbie = new User(username);
        users.add(newbie);
        //activeUsers.add(username);
    }
    //удаление пользователя из активных
    public void removeUserByName(String userName) {
        //activeUsers.remove(userName);
        users.stream().filter(u -> u.getName().equals(userName)).findFirst().ifPresent(o -> users.remove(o));
    }
}