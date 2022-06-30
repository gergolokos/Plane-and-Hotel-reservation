package hu.adatb.view.controller;

import hu.adatb.controller.AirportController;
import hu.adatb.controller.CityController;
import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AddAirportController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Double> latitudeSpinner;

    @FXML
    Spinner<Double> longitudeSpinner;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;

    @FXML
    ComboBox<City> cities;

    private List<Airport> airports;

    public AddAirportController() {
    }

    private Airport airport = new Airport();

    @FXML
    private void save(ActionEvent event) {
        airport.setCity(cities.getSelectionModel().getSelectedItem());

        if (AirportController.getInstance().add(airport)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőteret");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<City> cityList = CityController.getInstance().getAll();
        ObservableList<City> obsCityList = FXCollections.observableList(cityList);

        longitudeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                -180.0, 180, 0, 0.4));
        latitudeSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                -90.0, 90.0, 0, 0.4));

        cities.getItems().addAll(obsCityList);

        Callback<ListView<City>, ListCell<City>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(City item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        cities.setCellFactory(factory);
        cities.setButtonCell(factory.call(null));

        airports = AirportController.getInstance().getAll();

        airport.nameProperty().bind(nameField.textProperty());
        airport.latitudeProperty().bind(latitudeSpinner.valueProperty());
        airport.longitudeProperty().bind(longitudeSpinner.valueProperty());

        //longitudeSpinner.getValueFactory().valueProperty().bindBidirectional(airport.longitudeProperty().asObject());
        //latitudeSpinner.getValueFactory().valueProperty().bindBidirectional(airport.latitudeProperty().asObject());

        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var airport: airports) {
                if (newValue.equals(airport.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen név már létezik");
                saveButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(cities.valueProperty().isNull()));
    }
}
