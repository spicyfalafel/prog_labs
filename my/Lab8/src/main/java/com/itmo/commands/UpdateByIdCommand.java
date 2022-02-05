package com.itmo.commands;

import com.itmo.app.UIApp;
import com.itmo.exceptions.NotYourPropertyException;
import com.itmo.server.Application;
import com.itmo.client.User;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.server.ServerMain;
import com.itmo.server.notifications.AddNotification;
import com.itmo.server.notifications.RemoveNotification;
import com.itmo.utils.FieldsScanner;
import lombok.Setter;

public class UpdateByIdCommand extends Command{

    public UpdateByIdCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 1;
    }
    @Setter
    private Dragon dr;

    @Override
    public void clientInsertionFromConsole() {
        FieldsScanner fieldsScanner = FieldsScanner.getInstance();
        dr = fieldsScanner.scanDragon();
    }

    /**
     * апдейтит дракона по указанному id.
     * */
    @Override
    public String execute(Application application, User user) {
        try{
            long id = Long.parseLong(args[0].trim());
            Dragon prev = application.getCollection().findById(id);
            if(prev!=null){
                application.getCollection().remove(prev, user);
                dr.setId(id);
                String name = user.getName();
                dr.setOwnerName(name);
                dr.setUser(user);
                application.db.deleteDragonById(id);
                application.getCollection().add(dr);
                application.db.insertDragon(dr, id);
                application.notificationProducer.sendRemoveNotificationToAll(new RemoveNotification(id));
                application.notificationProducer.sendAddNotificationToAll(new AddNotification(dr));
                return ServerMain.localeClass.getString("dragon_was_added.text");
            }else{
                return ServerMain.localeClass.getString("dragon_with_id.text")
                        +" " + id + " " + ServerMain.localeClass.getString("not_found.text");
            }
        }catch (NumberFormatException e){
            return ServerMain.localeClass.getString("id_is_a_number.text");
        }catch (NotYourPropertyException e){
            return ServerMain.localeClass.getString("propriety.text") + e.getMessage();
        }
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("update_element_by_its_id.text");
    }
}