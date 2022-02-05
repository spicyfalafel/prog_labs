package com.itmo.utils;

import com.itmo.app.UIApp;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;

import java.io.IOException;

public class UIHelper {

    public static Image getImage(String path, Class c) {
        return new Image(c.getResourceAsStream(path));
    }

    public static Parent loadFxml(String path, Class c) throws IOException {
        return FXMLLoader.load(c.getResource(path), UIApp.localeClass.resourceBundle);
    }



    public static Parent loadFxmlWithController(String path, Initializable controller,
                                                Class c) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setResources(UIApp.localeClass.resourceBundle);
        loader.setLocation(c.getResource(path));
        return loader.load();
    }
}
