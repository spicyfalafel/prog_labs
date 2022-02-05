package com.itmo.server.notifications;

import com.itmo.app.controllers.MainWindowController;
import com.itmo.collection.dragon.classes.Dragon;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

public interface Notification  {
    public abstract void updateData();

}
