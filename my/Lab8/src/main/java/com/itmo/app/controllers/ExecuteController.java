package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.commands.ExecuteScriptCommand;
import com.itmo.utils.WindowsCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ExecuteController implements Initializable {
    @FXML
    private ListView<String> selectedFiles;

    @FXML
    private Button oneFileButton;

    @FXML
    private Button multiFileButton;

    @FXML
    private Button executeButton;

    private ObservableList<String> filesPath = FXCollections.observableArrayList();

    private ArrayList<File> files = new ArrayList<>();

    private FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedFiles.setItems(filesPath);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        oneFileButton.setOnAction(e -> clickOneFileButton());
        multiFileButton.setOnAction(e -> clickMultiFileButton());
        executeButton.setOnAction(e -> clickExecuteButton());
    }

    /*
        if one file button clicked
     */
    @FXML
    private void clickOneFileButton() {
        File file = fileChooser.showOpenDialog(WindowsCreator.createExecuteStage());
        if (file != null) {
            files.clear();
            files.add(file);
            showPath();
        }
    }

    @FXML
    private void clickMultiFileButton(){
        List<File> list = fileChooser.showOpenMultipleDialog(WindowsCreator.createExecuteStage());
        files.clear();
        files.addAll(list);
        showPath();
    }

    @FXML
    private void clickExecuteButton(){
        //close stage
        files.forEach(f -> {
            ExecuteScriptCommand executeCommand = new ExecuteScriptCommand(f);
            String answerFromAllCommands = executeCommand.getResult();
            WindowsCreator.createInfo(answerFromAllCommands, 1000, 1000).show();
            UIApp.getClient().sendCommandToServer(executeCommand);
            UIApp.getClient().getAnswerFromServer();
        });
    }

    private void showPath(){
        filesPath.clear();
        files.forEach(f -> filesPath.add(f.getPath()));
    }
}
