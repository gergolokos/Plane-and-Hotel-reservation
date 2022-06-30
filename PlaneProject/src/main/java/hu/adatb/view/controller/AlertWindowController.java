package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AlertController;
import hu.adatb.dao.AlertDaoImpl;
import hu.adatb.model.Alert;

import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AlertWindowController extends AlertDaoImpl implements Initializable {

    @FXML
    private TableView<Alert> table;

    @FXML
    private TableColumn<Alert, String> nameCol;


    @FXML
    private TableColumn<Alert, Void> actionsCol;

    public void refreshTable() {
        List<Alert> list = AlertController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @FXML
    public void addAlert() {
        try {
            App.DialogDeliver("add_alert.fxml","Figyelmeztetés hozzáadása");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public AlertWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Alert> list = AlertController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                {
                    deleteBtn.setOnAction(event -> {
                        Alert a = getTableView().getItems().get(getIndex());
                        delete(a.getId());
                        refreshTable();
                    });

                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox container = new HBox();
                        container.getChildren().add(deleteBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }

}