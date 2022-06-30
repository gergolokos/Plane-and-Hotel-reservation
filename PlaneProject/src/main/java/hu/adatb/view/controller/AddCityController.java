package hu.adatb.view.controller;


import hu.adatb.controller.CityController;
import hu.adatb.controller.CountryController;
import hu.adatb.model.City;
import hu.adatb.model.Country;
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


public class AddCityController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;

    @FXML
    ComboBox<Country> countries;

    private List<City> cities;

    public AddCityController() {
    }

    private City city = new City();

    @FXML
    private void save(ActionEvent event) {
        city.setCountry(countries.getSelectionModel().getSelectedItem());

        if (CityController.getInstance().add(city)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új várost");
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

        cities = CityController.getInstance().getAll();

        city.nameProperty().bind(nameField.textProperty());

        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var airport: cities) {
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
                .or(countries.valueProperty().isNull()));
    }
}
