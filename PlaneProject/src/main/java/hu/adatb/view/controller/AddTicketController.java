package hu.adatb.view.controller;

import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    @FXML
    GridPane grid;

    Button bookingButton = new Button("Foglalás");
    Label totalSumLabel = new Label("Végösszeg");

    List<ComboBox<Category>> categoryComboBoxes = new ArrayList<>();
    List<ComboBox<TravelClass>> travelClassComboBoxes = new ArrayList<>();

    int i;
    int countOfTicket;
    double distance;
    private int totalSum;
    Ticket ticket = new Ticket();
    Booking booking;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countOfTicket = AddBookingController.getCountOfTicket();
        booking = AddBookingController.getBooking();
        distance = AddBookingController.getDistance();

        PopulateComboBoxes();

        totalSum = (int) (distance * 80 + (countOfTicket * 4999));
        totalSumLabel.setText("Végösszeg: " + totalSum + " Ft");

        for(int i = 0; i < countOfTicket; i++) {
            categoryComboBoxes.get(i).valueProperty().addListener(observable -> CalculateBookingPrice());
            travelClassComboBoxes.get(i).valueProperty().addListener(observable -> CalculateBookingPrice());
        }

        bookingButton.setDefaultButton(true);
        totalSumLabel.setFont(new Font("System Bold", 15));

        grid.add(bookingButton, 2, ++i);
        grid.add(totalSumLabel, 1, i);
        grid.setVgap(20);

        FieldValidator();

        AddBooking();
    }

    private void CalculateBookingPrice() {
        totalSum = (int) distance * 80;
        for(int i = 0; i < countOfTicket; i++) {

            var category = categoryComboBoxes.get(i).getSelectionModel().getSelectedItem();
            var travelClass = travelClassComboBoxes.get(i).getSelectionModel().getSelectedItem();

            var discount = 0.0;
            var luxury = 1.0;

            if (category != null) {
                discount = (double) category.getDiscount() / 100;
            }

            if (travelClass != null){
                if (travelClass.getName().equals("First")) {
                    luxury = 1.4;
                } else if (travelClass.getName().equals("Business")) {
                    luxury = 1.7;
                }
            }

            var ticketPrice = 4999 * (1 - discount);
            System.out.println("Total sum before: " + totalSum);
            totalSum += (ticketPrice * luxury);
            System.out.println("Total sum after: " + totalSum);

            totalSumLabel.textProperty().bind(new SimpleStringProperty("Végösszeg: " + totalSum + " Ft"));
        }
    }

    private void PopulateComboBoxes() {
        var categories = CategoryController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();

        ObservableList<Category> obsCategoryList = FXCollections.observableList(categories);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);

        for (i = 0; i < countOfTicket; i++) {
            var label = new Label((i+1)+".jegy");
            var categoryComboBox = new ComboBox<Category>();
            var travelClassComboBox = new ComboBox<TravelClass>();
            categoryComboBox.setPromptText("Válassz kedvezményt");
            travelClassComboBox.setPromptText("Válassz utazási osztályt");

            categoryComboBox.getItems().addAll(obsCategoryList);
            Callback<ListView<Category>, ListCell<Category>> factory = lv -> new ListCell<>() {
                @Override
                protected void updateItem(Category item, boolean empty){
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getName());
                }
            };
            categoryComboBox.setCellFactory(factory);
            categoryComboBox.setButtonCell(factory.call(null));

            travelClassComboBox.getItems().addAll(obsTravelClassList);
            Callback<ListView<TravelClass>, ListCell<TravelClass>> factoryy = lv -> new ListCell<>() {
                @Override
                protected void updateItem(TravelClass item, boolean empty){
                    super.updateItem(item, empty);
                    setText(empty ? "" : item.getName());
                }
            };
            travelClassComboBox.setCellFactory(factoryy);
            travelClassComboBox.setButtonCell(factoryy.call(null));

            categoryComboBoxes.add(categoryComboBox);
            travelClassComboBoxes.add(travelClassComboBox);

            grid.add(label, 0, i);
            grid.add(categoryComboBox,1, i);
            grid.add(travelClassComboBox, 2, i);
        }
    }

    private void FieldValidator() {
        for (int i = 0; i < countOfTicket; i++) {
            bookingButton.disableProperty().bind(categoryComboBoxes.get(i).valueProperty().isNull().or(
                travelClassComboBoxes.get(i).valueProperty().isNull()
            ));
        }
    }

    private void AddBooking(){
        bookingButton.setOnAction(actionEvent -> {
            if (BookingController.getInstance().add(booking)) {
                for(int i = 0; i < countOfTicket; i++) {
                    ticket.setCategory(categoryComboBoxes.get(i).getSelectionModel().getSelectedItem());
                    ticket.setTravelClass(travelClassComboBoxes.get(i).getSelectionModel().getSelectedItem());
                    ticket.setBooking(booking);

                    TicketController.getInstance().add(ticket);
                }

                Utils.showInformation("Sikeres foglalás");
            }

            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
        });
    }
}
