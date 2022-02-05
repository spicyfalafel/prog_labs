package com.itmo.server;

import com.itmo.collection.DragonForTable;
import com.itmo.collection.MyDragonsCollection;
import com.itmo.database.DatabaseManager;
import com.itmo.server.notifications.AddNotification;
import com.itmo.server.notifications.NotificationProducer;
import com.itmo.utils.SerializationManager;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
    the purpose of this class is storing collection
    and handling active users
 */

public class Application implements Serializable {
    @Setter
    @Getter
    private MyDragonsCollection collection;
    private Date date;
    public DatabaseManager db;
    public ActiveUsersHandler activeUsers;
    public NotificationProducer notificationProducer;


    public Application() throws SQLException {
        db = new DatabaseManager();
        collection = new MyDragonsCollection(db.getCollectionFromDatabase());
        date = new Date();
        activeUsers = ActiveUsersHandler.getInstance();
        notificationProducer = new NotificationProducer();
    }

    public void getCollectionFromDB() {
        try {
            this.collection = new MyDragonsCollection(db.getCollectionFromDatabase());
        } catch (SQLException e) {
            System.out.println("ошибка в db");
        }
    }

    public Application(MyDragonsCollection collection) {
        this.collection = collection;
    }



    // after getting SubscribeForNotificationsCommand server sends all collection to socketchannel
    public void sendCollectionToClient(SocketChannel channel) {
        collection.getDragons().forEach( d -> {
            AddNotification addNotification = new AddNotification(d);
            notificationProducer.sendAddNotification(channel, addNotification);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
