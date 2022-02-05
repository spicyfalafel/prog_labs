package com.itmo.client;

import com.itmo.app.UIApp;
import com.itmo.commands.SubscribeForNotificationsCommand;
import com.itmo.server.notifications.AddNotification;
import com.itmo.server.notifications.Notification;
import com.itmo.utils.SerializationManager;
import com.itmo.utils.SerializationManager2;
import javafx.concurrent.Task;
import lombok.AllArgsConstructor;

import java.io.IOException;
@AllArgsConstructor
public class ListenerForNotifications extends Thread {
    String host;
    int port;
    @Override
    public void run() {
        Client c = new Client(host, port);
        c.connect();
        byte[] notificationInBytes;
        c.sendCommandToServer(new SubscribeForNotificationsCommand());
        SerializationManager2<Notification> sm = new SerializationManager2<>();
        //UIApp.mainWindowController.painter.run();
        while(true){
            try {
                notificationInBytes = c.getBytes();
                if (notificationInBytes!=null){
                    Notification notification = sm.readObject(notificationInBytes);
                    notification.updateData();
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
