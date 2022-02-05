package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.commands.RemoveByIdCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveByIdController implements Initializable {
    @FXML
    Label resultLabel;
    @FXML
    TextField idField;
    @FXML
    Button confirmButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(event -> {
            UIApp.getClient().sendCommandToServer(new RemoveByIdCommand(new String[]{idField.getText()}));
            String answer = UIApp.getClient().getAnswerFromServer();
            resultLabel.setText(answer);
        });
    }
}
