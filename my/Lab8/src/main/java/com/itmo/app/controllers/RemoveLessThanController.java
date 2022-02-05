package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.commands.RemoveLowerThanElementCommand;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveLessThanController implements Initializable {
    @FXML
    TextField valueField;
    @FXML
    Button confirmButton;
    @FXML
    javafx.scene.control.Label resultLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(e -> {
            try{
                float value = Float.parseFloat(valueField.getText());
                UIApp.getClient().sendCommandToServer(new RemoveLowerThanElementCommand(value));
                UIApp.getClient().getAnswerFromServer();
            }catch (NumberFormatException exception){
                resultLabel.setText(UIApp.localeClass.getString("couldnt_parse_fields_try_again.text"));
            }
        });
    }
}
