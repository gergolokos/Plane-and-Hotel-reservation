package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.dao.AirportDaoImpl;
import hu.adatb.model.Airport;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
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

public class AirportWindowController extends AirportDaoImpl implements Initializable {

    @FXML
    private TableView<Airport> table;

    @FXML
    private TableColumn<Airport, String> nameCol;

    @FXML
    private TableColumn<Airport, Double> longitudeCol;

    @FXML
    private TableColumn<Airport, Double> latitudeCol;

    @FXML
    private TableColumn<Airport, String> cityCol;

    @FXML
    private TableColumn<Airport, Void> actionsCol;

    public void refreshTable() {
        List<Airport> list = AirportController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @FXML
    public void addAirport() {
        try {
            App.DialogDeliver("add_airport.fxml","Repülőtér hozzáadása");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a hozzáadás ablakot");
        }
        refreshTable();
    }

    public AirportWindowController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Airport> list = AirportController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        longitudeCol.setCellValueFactory(new PropertyValueFactory<>("longitude"));
        latitudeCol.setCellValueFactory(new PropertyValueFactory<>("latitude"));
        cityCol.setCellValueFactory(cityName -> new SimpleStringProperty(cityName.getValue().getCity().getName()));
        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");

                {
                    deleteBtn.setOnAction(event -> {
                        Airport a = getTableView().getItems().get(getIndex());
                        delete(a.getId());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var selectedAirport = getTableView().getItems().get(getIndex());

                        DialogAirportController.setSelectedAirport(selectedAirport);
                        DialogAirportController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_airport.fxml", "Repülőtér módosítás");
                        } catch (IOException e) {
                            Utils.showWarning("Nem sikerült megnyitni a repülőtér módosító ablakot");
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