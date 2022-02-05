package com.itmo.commands;

import com.itmo.exceptions.NoSuchCommandException;
import com.itmo.exceptions.WrongArgumentsNumberException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * шаблон Команда.
 * вызывает команды
 */
public class CommandsInvoker {

    private static HashMap<String, Command> registeredCommands = new HashMap<>();

    private static CommandsInvoker instance;


    public static CommandsInvoker getInstance(){
        if (instance == null) {
            instance = new CommandsInvoker();
        }
        return instance;
    }
    private CommandsInvoker(){
    }

    public String getHelp(){
        //для сортировки по ключу (алфавиту)
        Map<String, Command> treeMap = new TreeMap<>(registeredCommands);
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, Command> entry : treeMap.entrySet()){
            builder.append(entry.getKey()).append(" : ").append(entry.getValue().getDescription()).append("\n");
        }
        return builder.toString();
    }

    /**
     * регистрирует команду, т.е. сопоставляет строчку с самой командой
     *
     * @param commandName строка, по которой будет вызываться команда
     * @param command     сама команда
     */
    public void register(String commandName, Command command){
        registeredCommands.put(commandName, command);
    }

    public void register(Map<String, Command> commandMap){
        registeredCommands.putAll(commandMap);
    }

    public Command validateCommand(String commandName, String[] arguments) throws NoSuchCommandException,
            WrongArgumentsNumberException {
        if(registeredCommands.containsKey(commandName)){
            Command command = registeredCommands.get(commandName);
            int requiredArgs = command.getNumberOfRequiredArgs();
            if(requiredArgs == arguments.length){
                command.setArgs(arguments);
                return registeredCommands.get(commandName);
            }else{
                throw new WrongArgumentsNumberException(requiredArgs, arguments.length);
            }
        }else {
            throw new NoSuchCommandException(commandName);
        }
    }
    /**
     * используется, например, в команде help
     *
     * @return мапа зарегистрированных команд
     */
    public HashMap<String, Command> getMapOfRegisteredCommands(){
        return registeredCommands;
    }
}