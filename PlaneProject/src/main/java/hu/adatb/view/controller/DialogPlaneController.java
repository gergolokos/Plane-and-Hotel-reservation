package hu.adatb.view.controller;

import hu.adatb.controller.PlaneController;
import hu.adatb.model.Plane;
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


public class DialogPlaneController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> seatsSpinner;

    @FXML
    Spinner<Integer> speedSpinner;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    private Plane plane = new Plane();
    private List<Plane> planes;
    private static Plane selectedPlane;
    private static boolean isAdd;

    public DialogPlaneController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (PlaneController.getInstance().add(plane)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőgépet");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (PlaneController.getInstance().update(plane)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni a módosított repülőgépet");
        }
    }

    @FXML
    private void cancel(ActionEvent event){
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        planes = PlaneController.getInstance().getAll();

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
            for (var plane: planes) {
                if (newValue.equals(plane.getName())) {
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
        nameField.setText(isAdd ? "" : selectedPlane.getName());
        speedSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                100, 2000, isAdd ? 100 : selectedPlane.getSpeed(), 10));
        seatsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                10, 1000, isAdd ? 10 : selectedPlane.getSeats(), 10));

        plane.nameProperty().bind(nameField.textProperty());
        plane.seatsProperty().bind(seatsSpinner.valueProperty());
        plane.speedProperty().bind(speedSpinner.valueProperty());
        plane.setId(selectedPlane.getId());
    }

    public static void setIsAdd(boolean isAdd) {
        DialogPlaneController.isAdd = isAdd;
    }

    public static void setSelectedPlane(Plane plane) {
        selectedPlane = plane;
    }

}
