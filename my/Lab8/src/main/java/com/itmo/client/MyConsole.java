package com.itmo.client;

import com.itmo.commands.*;
import com.itmo.exceptions.NoSuchCommandException;
import com.itmo.exceptions.WrongArgumentsNumberException;
import com.itmo.utils.FieldsScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MyConsole {
    private static CommandsInvoker invoker;
    private static BufferedReader streamToReadFrom
            = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    public MyConsole(){
        registerCommands();
    }
    public MyConsole(InputStream stream){
        streamToReadFrom = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        FieldsScanner.getInstance().configureScanner(new Scanner(streamToReadFrom));
        registerCommands();
    }

    // reads ONE line
    //
    public Command getFullCommandFromConsole(){
        Command command = null;
        try {
            command = readCommandFromConsole();
            command.clientInsertionFromConsole();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return command;
    }
    public Command getFullCommandFromStream(){
        Command command = null;
        try {
            command = readCommandFromStream();
            if(command!=null) command.clientInsertionFromConsole();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return command;
    }

    //read one line. if this line was command then return command.
    // else return null. if it was EOF return null
    public Command readCommandFromStream() throws IOException {
        Command command = null;
        String line;
        if((line = streamToReadFrom.readLine()) != null) {
            command = getCommandFromString(line);
            return command;
        }else{
            return null;
        }
    }

    // read one line. if it was a command return it. else repeat
    private Command readCommandFromConsole() throws IOException {
        Command command = null;
        String line;
        do{
            if((line = streamToReadFrom.readLine()) != null){
                command = getCommandFromString(line);
            }
        }while(command==null);
        return command;
    }
    // read a line. make sure it is registered command
    public static Command getCommandFromString(String command){
        String[] splitted = command.split(" ");
        String commandName = splitted[0];
        String[] arguments = new String[splitted.length - 1];
        System.arraycopy(splitted, 1, arguments, 0, splitted.length - 1);
        try {
            return invoker.validateCommand(commandName, arguments);
        } catch (WrongArgumentsNumberException | NoSuchCommandException e) {
            return null;
        }
    }

    public void registerCommands(){
        invoker = CommandsInvoker.getInstance();
        invoker.register("info", new InfoCommand());
        invoker.register("help", new HelpCommand());
        invoker.register("exit", new ExitCommand(null));
        invoker.register("clear", new ClearCommand(null));
        invoker.register("remove_by_id", new RemoveByIdCommand());
        invoker.register("add", new AddElementCommand());
        invoker.register("show", new ShowCommand(null));
        invoker.register("update", new UpdateByIdCommand(null));
        invoker.register("filter_starts_with_name", new FilterStartsWithNameCommand(null));
        invoker.register("add_if_max", new AddIfMaxCommand());
        invoker.register("add_if_min", new AddIfMinCommand());
        invoker.register("remove_lower", new RemoveLowerThanElementCommand(null));
        invoker.register("print_field_ascending_wingspan", new PrintFieldAscendingWingspanCommand(null));
        invoker.register("print_descending", new PrintDescendingCommand(null));
        invoker.register("execute_script", new ExecuteScriptCommand());
        invoker.register("login", new LoginCommand());
        invoker.register("register", new RegisterCommand());
    }
}