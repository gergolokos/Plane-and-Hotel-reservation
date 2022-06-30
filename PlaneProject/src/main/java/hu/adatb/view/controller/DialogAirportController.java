package hu.adatb.view.controller;

import hu.adatb.controller.AirportController;
import hu.adatb.model.Airport;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DialogAirportController implements Initializable {

    @FXML
    TextField nameField;


    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    private Airport airport = new Airport();
    private List<Airport> airports;
    private static Airport selectedAirport;
    private static boolean isAdd;

    public DialogAirportController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (AirportController.getInstance().add(airport)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőteret");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (AirportController.getInstance().update(airport)) {
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
        airports = AirportController.getInstance().getAll();

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
                addButton.disableProperty().bind(errorMsgName.textProperty().isNotEmpty());
            }
        });
    }

    private void InitTable() {
        nameField.setText(isAdd ? "" : selectedAirport.getName());
        airport.nameProperty().bind(nameField.textProperty());

    }

    public static void setIsAdd(boolean isAdd) {
        DialogAirportController.isAdd = isAdd;
    }

    public static void setSelectedAirport(Airport airport) {
        selectedAirport = airport;
    }

}

