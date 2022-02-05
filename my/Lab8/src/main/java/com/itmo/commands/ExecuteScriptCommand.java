package com.itmo.commands;

import com.itmo.client.MyConsole;
import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import com.itmo.server.ServerMain;
import com.itmo.utils.FieldsScanner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The type Execute script command.
 */
@NoArgsConstructor
public class ExecuteScriptCommand extends Command {
    private File fileToExecute;
    @Getter
    private String result;
    public ExecuteScriptCommand(File file){
        this.fileToExecute = file;
        MyConsole mc = new MyConsole();
        result = readCommandsFromFileExecuteAndGetAnswer(file);
    }

    public String readCommandsFromFileExecuteAndGetAnswer(File file) {
        Scanner scannerFromFile = null;
        try {
            scannerFromFile = new Scanner(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        StringBuilder builder = new StringBuilder();
        FieldsScanner.getInstance().configureScanner(scannerFromFile);
        // while not eof
        try {
            while(scannerFromFile.hasNextLine()){
                line = scannerFromFile.nextLine();
                Command command = MyConsole.getCommandFromString(line);
                // if it was existed command
                if(command!=null){
                    command.clientInsertionFromConsole();
                    UIApp.getClient().sendCommandToServer(command);
                    builder.append(UIApp.getClient().getAnswerFromServer()).append("\n");
                }
            }
        }catch (NoSuchElementException ignored){
        }
        return builder.toString();
    }

    public ExecuteScriptCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public void clientInsertionFromConsole() {
        fileToExecute = new File(args[0]);
    }

    @Override
    public String execute(Application application, User user) {
        return ServerMain.localeClass.getString("script_done.text");
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("execute_script_description.text");
    }
}
