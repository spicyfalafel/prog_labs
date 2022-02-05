package com.itmo.server.notifications;

import com.itmo.utils.SerializationManager;
import com.itmo.utils.SerializationManager2;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Set;

public class NotificationProducer implements Serializable {

    private SerializationManager2<Notification> sm = new SerializationManager2<>();
    ByteBuffer byteBuffer;
    public Set<SocketChannel> subscribers;

    public NotificationProducer() {
        subscribers = new HashSet<>();
    }

    public void sendRemoveNotificationToAll(RemoveNotification removeNotification){
        subscribers.forEach( sub -> {
            sendRemoveNotification(sub, removeNotification);
        });
    }
    public void sendAddNotificationToAll(AddNotification addNotification){
        subscribers.forEach( sub -> {
            sendAddNotification(sub, addNotification);
        });
    }


    public void sendAddNotification(SocketChannel socketChannel, AddNotification addNotification){
        try {
            byteBuffer = ByteBuffer.wrap(sm.writeObject(addNotification));
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRemoveNotification(SocketChannel socketChannel, RemoveNotification removeNotification){
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(SerializationManager.writeObject(removeNotification));
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void subscribeForNotifications(SocketChannel socketChannel){
        subscribers.add(socketChannel);
    }

    public void unsubscribe(SocketChannel socketChannel){
        subscribers.remove(socketChannel);
    }
}
