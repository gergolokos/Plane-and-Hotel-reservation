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
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DialogCityController implements Initializable {

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

    private City city = new City();
    private List<City> cities;
    private static City selectedCity;
    private static boolean isAdd;

    public DialogCityController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (CityController.getInstance().add(city)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőteret");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (CityController.getInstance().update(city)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni a módosított repülőteret");
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
        addButton.disableProperty().bind(nameField.textProperty().isEmpty());

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var city: cities) {
                if (newValue.equals(city.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen név már létezik");
                addButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void InitTable() {
        nameField.setText(isAdd ? "" : selectedCity.getName());
        city.setCountry(countries.getSelectionModel().getSelectedItem());
        city.nameProperty().bind(nameField.textProperty());

    }

    public static void setIsAdd(boolean isAdd) {
        DialogCityController.isAdd = isAdd;
    }

    public static void setSelectedCity(City city) {
        selectedCity = city;
    }

}

