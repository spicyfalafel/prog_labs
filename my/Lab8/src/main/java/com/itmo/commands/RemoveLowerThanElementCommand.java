package com.itmo.commands;

import com.itmo.server.Application;
import com.itmo.app.UIApp;
import com.itmo.client.User;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.server.notifications.RemoveNotification;
import com.itmo.utils.FieldsScanner;

import java.util.Set;

public class RemoveLowerThanElementCommand extends Command {
    private float value;
    public RemoveLowerThanElementCommand(float value){
        this.value = value;
        dr = new Dragon(value);
    }

    public RemoveLowerThanElementCommand(String[] args) {
        super(args);
    }

    @Override
    public int getNumberOfRequiredArgs() {
        return 0;
    }

    private Dragon dr;
    @Override
    public void clientInsertionFromConsole() {
        FieldsScanner helper = FieldsScanner.getInstance();
        dr = helper.scanDragon();
    }

    @Override
    public String execute(Application application, User user) {
        // todo notifications
        Set<Dragon> dragons = application.getCollection().getLowerThan(dr, user);
        if(dragons!=null) dragons.forEach(d -> {
            application.db.deleteDragonById(d.getId());
            application.notificationProducer.sendRemoveNotificationToAll(new RemoveNotification(d.getId()));
        });
        return application.getCollection().removeLower(dr, user);
    }

    @Override
    public String getDescription() {
        return UIApp.localeClass.getString("remove_all_elements_less_than.text");
    }
}
