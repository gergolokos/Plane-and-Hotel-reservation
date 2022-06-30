package hu.adatb.view.controller;


import hu.adatb.controller.CityController;
import hu.adatb.controller.CountryController;
import hu.adatb.controller.FlightController;
import hu.adatb.model.City;
import hu.adatb.model.Country;
import hu.adatb.model.Flight;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DialogFlightController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    ComboBox<Country> countries;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    private Flight flight = new Flight();
    private List<Flight> flights;
    private static Flight selectedFlight;
    private static boolean isAdd;

    public DialogFlightController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (FlightController.getInstance().add(flight)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új járatot");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (FlightController.getInstance().update(flight)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni a módosított járatot");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Country> countryList = CountryController.getInstance().getAll();
        ObservableList<Country> obsCountryList = FXCollections.observableList(countryList);


        countries.getItems().addAll(obsCountryList);

        Callback<ListView<Country>, ListCell<Country>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Country item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        countries.setCellFactory(factory);
        countries.setButtonCell(factory.call(null));
        flights = FlightController.getInstance().getAll();

        if(isAdd) {
            addButton.setVisible(true);
            editButton.setVisible(false);
        } else {
            addButton.setVisible(false);
            editButton.setVisible(true);
        }

        InitTable();
        FieldValidator();
    }

    private void FieldValidator() {
    }

    private void InitTable() {

    }

    public static void setIsAdd(boolean isAdd) {
        DialogFlightController.isAdd = isAdd;
    }

    public static void setSelectedFlight(Flight flight) {
        selectedFlight = flight;
    }

}

