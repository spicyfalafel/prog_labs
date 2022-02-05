package com.itmo.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Setter;
import java.net.URL;
import java.util.ResourceBundle;

public class InformationController implements Initializable {
    @FXML
    public Label infoLabel;

    @Setter
    private  String text="aaaaaaaa";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
