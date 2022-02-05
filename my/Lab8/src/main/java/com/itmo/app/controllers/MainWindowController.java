package com.itmo.app.controllers;

import com.itmo.app.UIApp;
import com.itmo.client.MyConsole;
import com.itmo.collection.DragonForTable;
import com.itmo.collection.dragon.classes.*;
import com.itmo.commands.*;
import com.itmo.utils.Pain;
import com.itmo.utils.UIHelper;
import com.itmo.utils.WindowsCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



/*
Класс должен контроллировать, что происходит в главном окне:
1) при нажатии на кнопки вызывать соответствующие функции, кнопки делятся на:
    а) команды: влияют на таблицу, могут вызывать другие окошки
    б) команда выход: то же самое, только переход к окно авторизации
    в) сменить язык
    д) что-то еще...
2) как-то обновлять таблицу
3) как-то рисовать драконов
 */

public class MainWindowController implements Initializable {
    @FXML
    private TabPane tableGraphTabPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem languageRussianItem, languageEstonianItem,
            languageSwedishItem, languageEspanItem;
    @FXML
    private Text currentUserText;
    @FXML
    public Rectangle colorOfUserRectangle;
    @FXML
    private Canvas xOyCanvas;
    @FXML
    private Button addButton,
            addIfMaxButton, addIfMinButton, clearButton, filterNameButton,
            infoButton, ascendingWingspanButton, sortValueButton,
            removeByIdButton, removeLowerThanButton, updateByIdButton,
    executeScriptButton, signOutButton, removeButton;

    @FXML
    private TableView<DragonForTable> dragonsTable;
    @FXML
    private TableColumn<DragonForTable, Long> idColumn;
    @FXML
    private TableColumn<DragonForTable, String> dragonNameColumn, creatorColumn, locationNameColumn;
    @FXML
    private TableColumn<DragonForTable, String> creationDateColumn;
    @FXML
    private TableColumn<DragonForTable, Integer> locationXColumn, xColumn;
    @FXML
    private TableColumn<DragonForTable, Long> locationYColumn, yColumn;
    @FXML
    private TableColumn<DragonForTable, Integer> ageColumn;
    @FXML
    private TableColumn<DragonForTable, Float> wingspanColumn;
    @FXML
    private TableColumn<DragonForTable, String> typeColumn;
    @FXML
    private TableColumn<DragonForTable, String> characterColumn;



    @FXML
    private TableColumn<DragonForTable, String> killerNameColumn;
    @FXML
    private TableColumn<DragonForTable, Float> locationZColumn;
    @FXML
    private TableColumn<DragonForTable, String> killerBirthDateColumn;
    @FXML
    private TableColumn<DragonForTable, String> killerHairColorColumn;
    @FXML
    private TableColumn<DragonForTable, String> killerNationalityColumn;
    @FXML
    private Label commandOutput;

    public ObservableList<DragonForTable> dragonsForTable;
    public Pain painter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleCommandButtons();
        handleLanguageMenuItems();
        handleHelpItem();
        handleOnClose();
        showUserName();
        handleTableView();
        handleDrawingGraph();
    }



    public void addDragonToColl(Dragon d){
        dragonsForTable.add(new DragonForTable(d));
    }


    public void handleTableView() {
        if(dragonsForTable==null){
            dragonsForTable = FXCollections.observableArrayList();
        }
        setUpColumns();
        dragonsTable.setItems(dragonsForTable);
        //sorting by id
        idColumn.setSortType(TableColumn.SortType.ASCENDING);
        dragonsTable.getSortOrder().add(idColumn);
    }



    private void setUpColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dragonNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        wingspanColumn.setCellValueFactory(new PropertyValueFactory<>("wingspan"));

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        characterColumn.setCellValueFactory(new PropertyValueFactory<>("character"));

        killerNameColumn.setCellValueFactory(new PropertyValueFactory<>("killerName"));
        killerBirthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdayInFormat"));
        killerHairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        killerNationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        locationXColumn.setCellValueFactory(new PropertyValueFactory<>("killerX"));
        locationYColumn.setCellValueFactory(new PropertyValueFactory<>("killerY"));
        locationZColumn.setCellValueFactory(new PropertyValueFactory<>("killerZ"));
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));

        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
    }


    public void showUserName() {
        if (!UIApp.getClient().getUser().getName().equals("unregistered")) {
            currentUserText.setText(UIApp.getClient().getUser().getName());
            colorOfUserRectangle.setFill(UIApp.getClient().getUser().getColor());
        }
    }


    private void handleHelpItem() {
        helpMenu.getItems().get(0).setOnAction(e -> {
            //initialization of commands...
            MyConsole console = new MyConsole();
            String help = CommandsInvoker.getInstance().getHelp();
            WindowsCreator.createInfo(help).show();
        });
    }

    private void handleLanguageMenuItems() {
        languageRussianItem.setOnAction(e -> changeLanguageInUI("RU"));
        languageEstonianItem.setOnAction(e -> changeLanguageInUI("EST"));
        languageSwedishItem.setOnAction(e -> changeLanguageInUI("SWE"));
        languageEspanItem.setOnAction(e -> changeLanguageInUI("SPA"));
    }

    private void handleOnClose() {
        UIApp.mainStage.setOnCloseRequest(e -> {
            UIApp.getClient().sendCommandToServer(new ExitCommand());
            UIApp.getClient().getAnswerFromServer();
        });
    }


    private void handleCommandButtons() {
        addButton.setOnAction(e -> {
            UIApp.addController.setType(AddController.TypeOfAdd.ADD);
            WindowsCreator.createAddForm().show();
        });

        addIfMaxButton.setOnAction(e -> {
            UIApp.addController.setType(AddController.TypeOfAdd.ADD_IF_MAX);
            WindowsCreator.createAddForm().show();
        });

        addIfMinButton.setOnAction(e -> {
            UIApp.addController.setType(AddController.TypeOfAdd.ADD_IF_MIN);
            WindowsCreator.createAddForm().show();
        });

        updateByIdButton.setOnAction(e -> {
            WindowsCreator.createUpdateById().show();
        });

        infoButton.setOnAction(e -> {
            UIApp.getClient().sendCommandToServer(new InfoCommand());
            String answer = UIApp.getClient().getAnswerFromServer();
            commandOutput.setText(answer);
        });

        removeByIdButton.setOnAction(e -> {
            WindowsCreator.createRemoveById().show();
        });

        clearButton.setOnAction(e -> {
            UIApp.getClient().sendCommandToServer(new ClearCommand());
            String answer = UIApp.getClient().getAnswerFromServer();
            commandOutput.setText(answer);
        });

        signOutButton.setOnAction(e -> {
            UIApp.getClient().sendCommandToServer(new SignOutCommand());
            String answer = UIApp.getClient().getAnswerFromServer();
            UIApp.authorizationStage.show();
            UIApp.mainStage.close();
        });

        removeButton.setOnAction(e -> {
            long id = dragonsTable.getSelectionModel().getSelectedItem().getId();
            UIApp.getClient().sendCommandToServer(new RemoveByIdCommand(id));
            String ans = UIApp.getClient().getAnswerFromServer();
            commandOutput.setText(ans);
        });

        filterNameButton.setOnAction(e -> {
            WindowsCreator.createFilterStartsWith().show();
        });

        ascendingWingspanButton.setOnAction(e -> {
            wingspanColumn.setSortType(TableColumn.SortType.DESCENDING);
            dragonsTable.getSortOrder().add(wingspanColumn);
        });
        sortValueButton.setOnAction( e -> {
            UIApp.getClient().sendCommandToServer(new PrintDescendingCommand(null));
            String ans = UIApp.getClient().getAnswerFromServer();
            WindowsCreator.createInfo(ans, 300, 300).show();
        });
        removeLowerThanButton.setOnAction(e -> {
            WindowsCreator.createInputValue().show();
        });

        executeScriptButton.setOnAction(e -> {
            WindowsCreator.createExecuteStage().show();
        });
    }

    private void handleDrawingGraph(){
        painter = new Pain(xOyCanvas);
        painter.drawAxis();
        xOyCanvas.setOnMouseClicked(e -> {
            double x = e.getX();
            double y = e.getY();
            double minDist = painter.MIN_DISTANCE;
            DragonForTable nearerDragon = null;
            for (DragonForTable d : dragonsForTable) {
                int distance = painter.calculateDistance(painter.dragonXToCanvasX(d.getX()), x,
                        painter.dragonYToCanvasY(d.getY()), y);
                if (distance < minDist) {
                    minDist = distance;
                    nearerDragon = d;
                }
            }

            if (nearerDragon != null) {
                int row = dragonsTable.getItems().indexOf(nearerDragon);
                dragonsTable.getSelectionModel().select(row);
                if (row != -1) {
                    commandOutput.setText("Element successfully found in table");
                    tableGraphTabPane.getSelectionModel().select(0);
                    return;
                }
                commandOutput.setText("Element not found in table");
            }
        });
    }

    private void changeLanguageInUI(String TAG) {
        UIApp.localeClass.changeLocaleByTag(TAG);
        // TODO changeDateFormat();
        reloadMainStage();
        UIApp.getClient().sendCommandToServer(
                new ChangeLanguageCommand(new String[]{TAG})
        );
        String ans = UIApp.getClient().getAnswerFromServer();
        commandOutput.setText(ans);
    }

    public void reloadMainStage() {
        Scene scene = UIApp.mainStage.getScene();
        try {
            scene.setRoot(UIHelper.loadFxmlWithController(
                    "/fxml/main.fxml",
                    UIApp.mainWindowController,
                    getClass()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        painter.drawCollection(dragonsForTable);
    }
}