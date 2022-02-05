package com.itmo.server.notifications;

import com.itmo.app.UIApp;
import com.itmo.app.controllers.MainWindowController;
import com.itmo.collection.DragonForTable;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.utils.SerializationManager;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class RemoveNotification implements Notification, Serializable {
    private long id;

    @Override
    public void updateData() {
        UIApp.mainWindowController.dragonsForTable.remove(new DragonForTable(id));
        UIApp.mainWindowController.painter.removeDragon(id);
        UIApp.mainWindowController.painter.drawAgain();
    }
}
