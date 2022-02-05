package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.commands.RemoveByIdCommand;
import com.itmo.commands.UpdateByIdCommand;
import com.itmo.database.DatabaseManager;
import com.itmo.utils.WindowsCreator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateByIdController implements Initializable {
    @FXML
    Label resultLabel;
    @FXML
    TextField idField;
    @FXML
    javafx.scene.control.Button confirmButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmButton.setOnAction(event -> {
            try {
                long id = Long.parseLong(idField.getText());
                UIApp.addController.setIdOfDragonToUpdate(id);
                UIApp.addController.setType(AddController.TypeOfAdd.UPDATE);
                WindowsCreator.createAddForm().show();
            } catch (NumberFormatException e){
                resultLabel.setText(UIApp.localeClass.getString("couldnt_parse_fields_try_again.text"));
            }
        });
    }
}
