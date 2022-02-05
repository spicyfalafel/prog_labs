package com.itmo.app.controllers;

import com.itmo.client.Client;
import com.itmo.collection.dragon.classes.Dragon;
import com.itmo.client.ListenerForNotifications;
import com.itmo.utils.Pain;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class State {
    public Client client;
    private ObservableList<Dragon> dragonsInTable;
    private String currentLang;
    private ListenerForNotifications listener;
    private Pain painter;
}