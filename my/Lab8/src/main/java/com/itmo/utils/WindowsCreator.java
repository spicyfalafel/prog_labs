package com.itmo.utils;

import com.itmo.app.UIApp;
import com.itmo.app.controllers.AddController;
import com.itmo.app.controllers.AuthorizationController;
import com.itmo.app.controllers.InformationController;
import com.itmo.client.Client;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowsCreator {

    public static Stage createAuthorization() {
        StackPane root = null;
        try {
            root = (StackPane) UIHelper
                    .loadFxmlWithController(
                            "/fxml/authorization.fxml",
                            UIApp.authorizationController,
                            WindowsCreator.class
                    );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("auth_title.text"), 745, 380);
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }

    public static Stage createAddForm() {
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/add.fxml",
                    UIApp.addController,
                    WindowsCreator.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("add_title.text"));
        stage.setResizable(false);
        setIconToStage(stage);
        return stage;
    }



    public static Stage createRemoveById() {
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/id_input.fxml",
                    UIApp.removeByIdController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("remove_by_id.text"));
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }

    public static Stage createUpdateById() {
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/id_input.fxml",
                    UIApp.updateByIdController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("update_by_id.text"));
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }
    public static Stage createFilterStartsWith() {
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/filter_starts_with.fxml",
                    UIApp.filterStartsWithController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("filter_starts_with.text"));
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }
    public static Stage createInputValue() {
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/input_value.fxml",
                    UIApp.removeLessThanController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("remove.text"));
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }

    public static Stage createError() {
        VBox root = null;
        try {
            root = (VBox) UIHelper.loadFxmlWithController(
                    "/fxml/error.fxml",
                    UIApp.errorController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("error.text"));
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }

    public static Stage createInfo(String information, int width, int height){
        VBox root = null;
        try {
            root = (VBox) UIHelper.loadFxmlWithController(
                    "/fxml/info.fxml",
                    UIApp.informationController,
                    UIApp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UIApp.informationController.infoLabel.setText(information);
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("information.text"), width, height);
        setIconToStage(stage);
        stage.setResizable(false);
        return stage;
    }


    public static Stage createInfo(String information){
        return createInfo(information, 1000, 800);
    }

    public static Stage createExecuteStage(){
        AnchorPane root = null;
        try {
            root = (AnchorPane) UIHelper.loadFxmlWithController(
                    "/fxml/execute.fxml",
                    UIApp.executeController,
                    WindowsCreator.class
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = createBlankStage(root, UIApp.localeClass.getString("execute.text"));
        stage.setResizable(false);
        setIconToStage(stage);
        return stage;
    }

    public static Stage createBlankStage(Parent layout, String title){
        Scene scene = new Scene(layout);
        Stage newWindow = new Stage();
        newWindow.setTitle(title);
        scene.getStylesheets().add("css/every.css");
        newWindow.setScene(scene);
        return newWindow;
    }

    public static Stage createBlankStage(Parent layout, String title, int w, int h){
        Stage newWindow = createBlankStage(layout, title);
        newWindow.setWidth(w);
        newWindow.setHeight(h);
        return newWindow;
    }

    public static void setIconToStage(Stage stage){
        Image icon = UIHelper.getImage("/images/icons/icon_mortal_kombat.png", WindowsCreator.class);
        stage.getIcons().add(icon);
    }
}
