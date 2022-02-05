package com.commands;

import com.Request;
import com.exceptions.InvalidAmountOfArgumentsException;

import static com.Parsers.verify;

public class ExecuteScriptCommand extends AbstractCommand {

    private String filename;

    public ExecuteScriptCommand() {
        super(CommandType.EXECUTE_SCRIPT);

    }

    public Request execute(String[] commandSplit) {
        try {
            verify(commandSplit, 1);
            this.filename = commandSplit[1];
            return getRequest();
        } catch (InvalidAmountOfArgumentsException e) {
            e.printMessage();
        }
        return new Request(null);
    }

    public String getFilename() {
        return filename;
    }
}
