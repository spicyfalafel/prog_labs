package com.itmo.app;

import com.itmo.app.controllers.*;
import com.itmo.client.Client;
import com.itmo.client.ListenerForNotifications;
import com.itmo.client.MainUI;
import com.itmo.commands.GetColorCommand;
import com.itmo.utils.LocaleClass;
import com.itmo.utils.UIHelper;
import com.itmo.utils.UTF8Control;
import com.itmo.utils.WindowsCreator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/*
    this class starts UI (auth window and main window)
*/

public class UIApp extends Application{
    @Setter @Getter
    private static Client client;
    @Getter
    public static MainWindowController mainWindowController;
    @Getter
    public static AuthorizationController authorizationController;
    public static ErrorController errorController;
    public static InformationController informationController;
    public static AddController addController;
    public static RemoveByIdController removeByIdController;
    public static UpdateByIdController updateByIdController;
    public static FilterStartsWithController filterStartsWithController;
    public static RemoveLessThanController removeLessThanController;
    public static ExecuteController executeController;

    public static LocaleClass localeClass;
    public static Stage mainStage;
    public static Stage authorizationStage;
    public static ResourceBundle resourceBundle;

    public void run(String[] args){
        launch();
    }


    @Override
    public void init()  {
        authorizationController = new AuthorizationController();
        mainWindowController = new MainWindowController();
        addController = new AddController();
        errorController = new ErrorController();
        informationController = new InformationController();
        removeByIdController = new RemoveByIdController();
        updateByIdController = new UpdateByIdController();
        filterStartsWithController = new FilterStartsWithController();
        removeLessThanController = new RemoveLessThanController();
        executeController = new ExecuteController();
        localeClass = new LocaleClass();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        resourceBundle = ResourceBundle.getBundle("locals", Locale.forLanguageTag("RU"), new UTF8Control());
        mainStage = primaryStage;
        authorizationStage = WindowsCreator.createAuthorization();
        authorizationStage.show();
        initPrimaryStage(primaryStage);
        ListenerForNotifications notificationListener = new ListenerForNotifications(MainUI.host, MainUI.port);
        notificationListener.setDaemon(true);
        notificationListener.start();
    }

    private void initPrimaryStage(Stage primaryStage) throws IOException {
        VBox root = (VBox) UIHelper
                .loadFxmlWithController("/fxml/main.fxml", mainWindowController, getClass());
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(localeClass.getString("lab8.text"));
        WindowsCreator.setIconToStage(primaryStage);
        primaryStage.getScene().getStylesheets().add("css/main.css");
        primaryStage.setOnShown(e ->{
            mainWindowController.reloadMainStage();

            client.sendCommandToServer(new GetColorCommand());
            String ans = client.getAnswerFromServer();
            String[] split = ans.split(" ");
            double[] rgb = new double[3];
            for (int i = 0; i<3;i++) {
                rgb[i] = Double.parseDouble(split[i]);
            }
            mainWindowController.colorOfUserRectangle.setFill(Color.color(rgb[0], rgb[1], rgb[2]));
        });
    }

    @Override
    public void stop()  {
        client.closeEverything();
    }
}