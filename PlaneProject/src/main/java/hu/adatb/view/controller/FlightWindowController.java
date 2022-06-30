package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.FlightController;

import hu.adatb.dao.FlightDaoImpl;


import hu.adatb.model.Flight;

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

public class FlightWindowController extends FlightDaoImpl implements Initializable{

    @FXML
    private TableView<Flight> table;

    @FXML
    private TableColumn<Flight, String> felCol;

    @FXML
    private TableColumn<Flight, String> fromCol;

    @FXML
    private TableColumn<Flight, String> toCol;


    @FXML
    private TableColumn<Flight, Integer> seatsCol;


    @FXML
    private TableColumn<Flight, Void> actionsCol;

    @FXML
    public void addFlight() {
        DialogFlightController.setIsAdd(true);

        try {
            App.DialogDeliver("dialog_flight.fxml", "Járat hozzáadás");
        } catch (IOException e) {
            Utils.showWarning("Feljesztés alatt :)");
        }
        refreshTable();
    }

    public FlightWindowController() {
    }

    public void refreshTable() {
        List<Flight> list = FlightController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Flight> list = FlightController.getInstance().getAll();
        table.setItems(FXCollections.observableList(list));

        InitTable();
    }

    private void InitTable() {
        felCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getDateTimeInRightFormat()));
        fromCol.setCellValueFactory(cityName -> new SimpleStringProperty(cityName.getValue().getFromAirport().getName()));
        toCol.setCellValueFactory(cityName -> new SimpleStringProperty(cityName.getValue().getToAirport().getName()));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));

        actionsCol.setCellFactory(param -> {
            return new TableCell<>() {
                private final Button deleteBtn = new Button("Törlés");
                private final Button editBtn = new Button("Módosítás");

                {
                    deleteBtn.setOnAction(event -> {
                        Flight f = getTableView().getItems().get(getIndex());
                        delete(f.getId());
                        refreshTable();
                    });

                    editBtn.setOnAction(event -> {
                        var SelectedFlight = getTableView().getItems().get(getIndex());

                        DialogFlightController.setSelectedFlight(SelectedFlight);
                        DialogFlightController.setIsAdd(false);

                        try {
                            App.DialogDeliver("dialog_flight.fxml", "Repülőgép módosítás");
                        } catch (IOException e) {
                            Utils.showWarning("Fejlesztés alatt :)");
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

    @Override
    public boolean update(Flight flight) {
        return false;
    }
}