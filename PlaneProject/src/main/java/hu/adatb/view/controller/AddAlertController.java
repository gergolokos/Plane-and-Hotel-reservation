package hu.adatb.view.controller;


import hu.adatb.controller.AlertController;
import hu.adatb.controller.CityController;

import hu.adatb.model.Alert;
import hu.adatb.model.City;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddAlertController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Label errorMsgName;

    @FXML
    Button saveButton;


    private List<Alert> alerts;

    public AddAlertController() {
    }

    private Alert alert = new Alert();

    @FXML
    private void save(ActionEvent event) {
        //alert.setCountry(countries.getSelectionModel().getSelectedItem());

        if (AlertController.getInstance().add(alert)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új figyelelmeztetést");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        alerts = AlertController.getInstance().getAll();

        alert.messageProperty().bind(nameField.textProperty());

        FieldValidator();

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var alert: alerts) {
                if (newValue.equals(alert.getMessage())) {
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
        saveButton.disableProperty().bind(nameField.textProperty().isEmpty());
    }
}
