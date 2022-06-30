package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.CityController;
import hu.adatb.dao.CityDaoImpl;
import hu.adatb.model.City;
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

public class CityWindowController extends CityDaoImpl implements Initializable {

    @FXML
    private TableView<City> table;

    @FXML
    private TableColumn<City, String> nameCol;


    @FXML
    private TableColumn<City, Void> actionsCol;

    public void refreshTable() {
        List<City> list = CityController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @FXML
    public void addCity() {
        try {
            App.DialogDeliver("add_city.fxml","Város hozzáadása");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public CityWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<City> list = CityController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");

                {
                    deleteBtn.setOnAction(event -> {
                        City a = getTableView().getItems().get(getIndex());
                        delete(a.getId());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var selectedCity = getTableView().getItems().get(getIndex());

                        DialogCityController.setSelectedCity(selectedCity);
                        DialogCityController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_city.fxml", "Város módosítás");
                        } catch (IOException e) {
                            Utils.showWarning("Nem sikerült megnyitni a város módosító ablakot");
                        }
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
                        container.getChildren().addAll(deleteBtn, editBtn);
                        setGraphic(container);
                    }
                }
            };

        });
    }

}