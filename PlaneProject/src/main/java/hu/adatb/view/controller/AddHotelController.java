package hu.adatb.view.controller;


import hu.adatb.controller.CityController;
import hu.adatb.controller.HotelController;
import hu.adatb.model.City;
import hu.adatb.model.Hotel;
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

public class AddHotelController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> starSpinner;


    @FXML
    ComboBox<City> cities;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;


    private List<Hotel> hotels;

    public AddHotelController() {
    }

    private Hotel hotel = new Hotel();

    @FXML
    private void save(ActionEvent event) {
        hotel.setCity(cities.getSelectionModel().getSelectedItem());

        if (HotelController.getInstance().add(hotel)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új szállodát");
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

        starSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, 5, 0, 1));

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

        hotels = HotelController.getInstance().getAll();

        hotel.nameProperty().bind(nameField.textProperty());
        hotel.starsProperty().bind(starSpinner.valueProperty());


        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var airport: hotels) {
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
