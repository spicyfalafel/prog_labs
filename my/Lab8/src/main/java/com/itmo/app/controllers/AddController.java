package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.collection.DragonWithStringFields;
import com.itmo.collection.dragon.classes.*;
import com.itmo.commands.*;
import com.itmo.utils.FieldsValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;


public class AddController implements Initializable {
    @FXML
    private TextField dragonNameField, xField,
            yField, wingspanField, ageField, killerNameField,
            birthdateField, locationNameField,
            locationXField, locationYField, locationZField;
    @FXML
    private ChoiceBox<DragonType> typeBox;
    @FXML
    private ChoiceBox<DragonCharacter> characterBox;
    @FXML
    private ChoiceBox<Color> hairColorBox;
    @FXML
    private ChoiceBox<Country> nationalityBox;
    @FXML
    private Button addElementButton;
    @FXML
    private Label stateText;
    @Setter
    private TypeOfAdd type;

    @Setter @Getter
    private long idOfDragonToUpdate = 0;

    public enum TypeOfAdd{
        ADD,
        ADD_IF_MIN,
        ADD_IF_MAX,
        UPDATE
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeChoiceBoxes();
        initializeButtons();
    }

    void initializeButtons() {
        addElementButton.setOnAction(e -> {
            Dragon dragonFromForm = getDragonFromForm();
            if(dragonFromForm == null){
                stateText.setTextFill(Paint.valueOf("RED"));
                stateText.setMaxWidth(300);
                stateText.setWrapText(true);
                stateText.setText(UIApp.localeClass.getString("couldnt_parse_fields_try_again.text"));
                return;
            }
            Command command;
            switch (type){
                case ADD_IF_MAX:
                    command = new AddIfMaxCommand(dragonFromForm);
                    break;
                case ADD_IF_MIN:
                    command = new AddIfMinCommand(dragonFromForm);
                    break;
                default:
                case ADD:
                    command = new AddElementCommand(dragonFromForm);
                    break;
                case UPDATE:
                    UpdateByIdCommand command1 = new UpdateByIdCommand(new String[]{Long.toString(idOfDragonToUpdate)});
                    command1.setDr(dragonFromForm);
                    command = command1;
                    break;
            }
            UIApp.getClient().sendCommandToServer(command);
            String ans = UIApp.getClient().getAnswerFromServer();
            stateText.setText(ans);
        });
    }

    void initializeChoiceBoxes() {
        ObservableList<DragonType> types = FXCollections.observableArrayList(DragonType.values());
        typeBox.setItems(types);
        ObservableList<DragonCharacter> characters = FXCollections.observableArrayList(DragonCharacter.values());
        characterBox.setItems(characters);
        characterBox.setValue(DragonCharacter.GOOD);
        ObservableList<Color> colors = FXCollections.observableArrayList(Color.values());
        hairColorBox.setItems(colors);
        ObservableList<Country> countries = FXCollections.observableArrayList(Country.values());
        nationalityBox.setItems(countries);
    }



    private Dragon getDragonFromForm() {
        FieldsValidator fieldsValidator = new FieldsValidator(getDragonStringsFromForm());
        if(fieldsValidator.isMainFieldsOK()){
            String name = dragonNameField.getText();
            int x = Integer.parseInt(xField.getText());
            long y = Long.parseLong(yField.getText());
            float wingspan = Float.parseFloat(wingspanField.getText());
            int age = Integer.parseInt(ageField.getText());
            DragonType type = typeBox.getValue();
            DragonCharacter character = characterBox.getValue();
            Person killer = null;
            if(fieldsValidator.isKillerFieldsEmpty()){
                return new Dragon(name, new Coordinates(x, y),
                        age, wingspan, type, character,
                        killer);
            }else if (fieldsValidator.isKillerFieldsRequredNotEmpty()){
                String killerName = killerNameField.getText();
                String birthdate = birthdateField.getText();
                String locationName = locationNameField.getText();
                Color hairColor = hairColorBox.getValue();
                Country nationality = nationalityBox.getValue();
                int locationX = Integer.parseInt(locationXField.getText());
                Long locationY = Long.parseLong(locationYField.getText());
                Float locationZ = Float.parseFloat(locationZField.getText());
                killer = new Person(killerName, hairColor, nationality,
                        new Location(locationX, locationY, locationZ, locationName));
                killer.setBirthdayInFormat(birthdate);
                return new Dragon(name, new Coordinates(x, y),
                        age, wingspan, type, character,
                        killer);
            }
            return null;
        }else return null;
    }

    private DragonWithStringFields getDragonStringsFromForm(){

        DragonWithStringFields d = new DragonWithStringFields();

        String name = dragonNameField.getText();
        d.setName(name);
        String x = xField.getText();
        d.setX(x);
        String y = yField.getText();
        d.setY(y);
        String wingspan = wingspanField.getText();
        d.setWingspan(wingspan);
        String age = ageField.getText();
        d.setAge(age);
        String killerName = killerNameField.getText();
        d.setKillerName(killerName);
        String birthdate = birthdateField.getText();
        d.setBirthday(birthdate);
        String locationName = locationNameField.getText();
        d.setLocName(locationName);
        String locationX = locationXField.getText();
        d.setKillerX(locationX);
        String locationY = locationYField.getText();
        d.setKillerY(locationY);
        String locationZ = locationZField.getText();
        d.setKilllerZ(locationZ);

        DragonType type = typeBox.getValue();
        d.setType(type);
        DragonCharacter character = characterBox.getValue();
        d.setCharacter(character);
        Color hairColor = hairColorBox.getValue();
        d.setHairColor(hairColor);
        Country nationality = nationalityBox.getValue();
        d.setNationality(nationality);
        return d;
    }
}