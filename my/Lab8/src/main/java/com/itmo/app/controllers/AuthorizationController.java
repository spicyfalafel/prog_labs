package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.client.Client;
import com.itmo.client.ListenerForNotifications;
import com.itmo.client.MainUI;
import com.itmo.commands.ChangeLanguageCommand;
import com.itmo.commands.LoginCommand;
import com.itmo.commands.RegisterCommand;
import com.itmo.utils.LocaleClass;
import com.itmo.utils.UIHelper;
import com.itmo.utils.UTF8Control;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class AuthorizationController implements Initializable {
    @FXML
    private ImageView dragon_good, dragon_bad;
    @FXML
    private Button buttonLogin, buttonRegister;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private SplitMenuButton languageSplitMenu;
    @FXML
    private MenuItem russianLanguageMenuItem,
            spanishLanguageMenuItem, swedenLanguageMenuItem,
            estonianLanguageMenuItem;
    @FXML
    private StackPane stackPane;
    @FXML
    private Text labelMessage;

    private String login;
    private String password;



    public AuthorizationController(){}


    public void initialize(URL location, ResourceBundle resources) {
        setImages();
        buttonLogin.setOnAction(loginButtonHandler);
        buttonRegister.setOnAction(registerButtonHandler);
        StackPane.setAlignment(languageSplitMenu, Pos.TOP_RIGHT);
        initializeLanguageMenuItems();
    }


    private void initializeLanguageMenuItems() {
        russianLanguageMenuItem.setOnAction(changeLangToRussian);
        estonianLanguageMenuItem.setOnAction(changeLangToEstonian);
        swedenLanguageMenuItem.setOnAction(changeLangToSweden);
        spanishLanguageMenuItem.setOnAction(changeLangToSpanish);
    }


    private void changeLanguageInUI(String TAG) {
        UIApp.localeClass.changeLocaleByTag(TAG);
        Scene scene = UIApp.authorizationStage.getScene();
        // TODO changeDateFormat();
        try {
            scene.setRoot(UIHelper.loadFxmlWithController(
                    "/fxml/authorization.fxml",
                    UIApp.authorizationController,
                    getClass()));
            UIApp.getClient().sendCommandToServer(
                    new ChangeLanguageCommand(new String[]{TAG})
            );
            String ans = UIApp.getClient().getAnswerFromServer();
            labelMessage.setText(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private final EventHandler<ActionEvent> loginButtonHandler = event -> {
        login = loginTextField.getCharacters().toString();
        password = passwordField.getText();
        LoginCommand loginCommand = new LoginCommand(login, password);
        UIApp.getClient().sendCommandToServer(loginCommand);
        String ans = UIApp.getClient().getAnswerFromServer();

        labelMessage.setText(ans);

        if (ans.startsWith(UIApp.localeClass.getString("hello.text"))) {
            UIApp.mainStage.show();
            UIApp.authorizationStage.close();

        }
        event.consume();
    };

    private final EventHandler<ActionEvent> registerButtonHandler = event -> {
        login = loginTextField.getCharacters().toString();
        password = passwordField.getText();
        RegisterCommand registerCommand = new RegisterCommand(login, password);
        UIApp.getClient().sendCommandToServer(registerCommand);
        String ans = UIApp.getClient().getAnswerFromServer();
        labelMessage.setText(ans);
    };

    private final EventHandler<ActionEvent> changeLangToRussian = event -> {
        changeLanguageInUI("RU");
    };

    private final EventHandler<ActionEvent> changeLangToEstonian = event -> {
        changeLanguageInUI("EST");
    };

    private final EventHandler<ActionEvent> changeLangToSweden = event -> {
        changeLanguageInUI("SWE");
    };

    private final EventHandler<ActionEvent> changeLangToSpanish = event -> {
        changeLanguageInUI("SPA");
    };


    private void setImages() {
        Image good = UIHelper.getImage("/images/dragon_good.png", getClass());
        dragon_good.setImage(good);
        Image bad = UIHelper.getImage("/images/dragon_bad11.png", getClass());
        dragon_bad.setImage(bad);
    }
}