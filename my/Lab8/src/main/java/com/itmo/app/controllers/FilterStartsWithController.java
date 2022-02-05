package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.commands.FilterStartsWithNameCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FilterStartsWithController implements Initializable {
    @FXML
    TextField nameField;
    @FXML
    Button confirmButton;
    @FXML
    javafx.scene.control.Label resultLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(e -> {
            String drName = nameField.getText();
            UIApp.getClient().sendCommandToServer(new FilterStartsWithNameCommand(new String[]{drName}));
            resultLabel.setText(UIApp.getClient().getAnswerFromServer());
        });
    }
}
