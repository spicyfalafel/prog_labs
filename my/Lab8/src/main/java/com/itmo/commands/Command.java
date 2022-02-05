package com.itmo.commands;

import com.itmo.app.UIApp;
import com.itmo.client.User;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Command pattern
 */
public abstract class Command implements Executable, Serializable {
    @Getter @Setter
    private User user;
    protected String[] args;

    @Getter @Setter
    protected boolean noRightsToExecute = false;

    public void clientInsertionFromConsole(){
    }

    /**
     * у команд типа AddElement, AddIfMin, AddIfMax, UpdateIdCommand метод возвращает 0, т.к.
     * элемент вводится построчно
     *
     * @return количество аргументов у команды
     */
    abstract public int getNumberOfRequiredArgs();


    public Command(String[] args){
        this.args = args;
    }

    public Command(){}

    public void setArgs(String[] args){
        this.args = args;
    }
    /**
     * Get description string.
     *
     * @return discription of the command
     */
    public String getDescription(){
        return UIApp.localeClass.getString("lazy_developer_didnt_write_command_description.text");
    }
}
