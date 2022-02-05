package com.itmo.commands;

import com.itmo.app.UIApp;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.exceptions.NotYourPropertyException;
import com.itmo.server.Application;
import com.itmo.client.User;
import com.itmo.server.ServerMain;
import com.itmo.server.notifications.AddNotification;
import com.itmo.server.notifications.RemoveNotification;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class RemoveByIdCommand extends Command {

    long id;
    public RemoveByIdCommand(long id){
        this.id = id;
    }

    public RemoveByIdCommand(String[] args) {
        super(args);
        this.id = Long.parseLong(args[0]);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }


    @Override
    public String execute(Application application, User user) {
        try{
            if(application.getCollection().removeById(id, user)){
                application.db.deleteDragonById(id);
                application.notificationProducer.sendRemoveNotificationToAll(
                        new RemoveNotification(id)
                );
                return ServerMain.localeClass.getString("dragon_with_id.text")
                                + id + " " +
                        ServerMain.localeClass.getString("was_removed.text");
            }else{
                return ServerMain.localeClass.getString("no_such_dragon_with_id.text")
                        + " " + id;
            }
        }catch (NumberFormatException e){
            return ServerMain.localeClass.getString("id_must_be_more_than_zero.text");
        }catch (NotYourPropertyException e){
            return ServerMain.localeClass.getString("propriety.text") + " "+ e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("remove_by_id.text");
    }
}
