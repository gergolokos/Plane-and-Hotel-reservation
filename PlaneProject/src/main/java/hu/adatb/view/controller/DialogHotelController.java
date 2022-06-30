package hu.adatb.view.controller;

import hu.adatb.controller.HotelController;
import hu.adatb.model.Hotel;
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

public class DialogHotelController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    Spinner<Integer> starSpinner;

    @FXML
    Label errorMsgName;

    @FXML
    Button addButton;

    @FXML
    Button editButton;

    private Hotel hotel = new Hotel();
    private List<Hotel> hotels;
    private static Hotel selectedHotel;
    private static boolean isAdd;

    public DialogHotelController() {
    }

    @FXML
    private void save(ActionEvent event) {
        if (HotelController.getInstance().add(hotel)) {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőgépet");
        }
    }

    @FXML
    public void edit(ActionEvent event) {
        if (HotelController.getInstance().update(hotel)) {
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
        hotels = HotelController.getInstance().getAll();

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
            for (var plane: hotels) {
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
        nameField.setText(isAdd ? "" : selectedHotel.getName());
        starSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, 5, isAdd ? 1 : selectedHotel.getStars(), 1));

        hotel.nameProperty().bind(nameField.textProperty());
        hotel.starsProperty().bind(starSpinner.valueProperty());
        hotel.setId(selectedHotel.getId());
    }

    public static void setIsAdd(boolean isAdd) {
        DialogHotelController.isAdd = isAdd;
    }

    public static void setSelectedHotel(Hotel hotel) {
        selectedHotel = hotel;
    }

}
