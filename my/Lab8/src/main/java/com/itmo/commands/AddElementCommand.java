package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.server.ServerMain;
import com.itmo.server.notifications.AddNotification;
import com.itmo.utils.FieldsScanner;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
public class AddElementCommand extends Command {

    private Dragon dr=null;

    public AddElementCommand(String[] args) {
    }
    public AddElementCommand(Dragon d){
        dr = d;
    }

    @Override
    public void clientInsertionFromConsole() {
        FieldsScanner fieldsScanner = FieldsScanner.getInstance();
        this.dr = fieldsScanner.scanDragon();
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }


    @Override
    public String execute(Application application, User user){
        dr.setCreationDate(new Date());
        dr.setOwnerName(user.getName());
        dr.setUser(user);

        application.db.insertDragon(dr);
        application.getCollectionFromDB();

        dr.setId(application.db.getIdOfDragon(dr));

        dr.getUser().setColor(application.db.getColorOfUser(user.getName()));
        application.notificationProducer.sendAddNotificationToAll(
                new AddNotification(dr)
        );
        return ServerMain.localeClass.getString("dragon_was_added.text");
    }

    /**
     *
     * @return описание команды
     */
    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("add_new_element_into_collection.text");
    }
}
