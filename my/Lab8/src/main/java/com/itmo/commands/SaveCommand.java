package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.client.User;
import com.itmo.utils.XmlStaff;
import com.itmo.server.ServerMain;

import java.io.FileNotFoundException;

public class SaveCommand extends Command {

    public SaveCommand(String[] args) {
        super(args);
    }
    public SaveCommand(){
        this.args = new String[]{"input.xml"};
    }
    public SaveCommand(String resFileName){
        this.args = new String[]{resFileName};
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }

    @Override
    public String execute(Application application, User user) {
        try{
            XmlStaff.writeCollectionToFile(application.getCollection().getDragons(), args[0]);
            ServerMain.logger.info("save done");
            return "save done";
        }catch (FileNotFoundException e){
            ServerMain.logger.info("файл для сохранения не найден. возможно препод убрал права на файл или директорию.");
            return "файл для сохранения не найден.";
        }
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
