package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.*;
import hu.adatb.model.*;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookingController implements Initializable {

    @FXML
    ComboBox<Payment> paymentComboBox;

    @FXML
    ComboBox<Category> categoryComboBox;

    @FXML
    ComboBox<TravelClass> travelClassComboBox;

    @FXML
    Spinner<Integer> ticketSpinner;

    @FXML
    Button ticketSelector;

    @FXML
    Button bookingButton;

    @FXML
    CheckBox sameTickets;

    @FXML
    Label totalSumLabel;

    private static Booking booking = new Booking();
    private Ticket ticket = new Ticket();

    private Flight bookedFlight;

    private static int countOfTicket = 1;
    private int totalSum;
    private static double distance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookedFlight = FlightListController.getBookedFlight();

        ticketSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1,(Math.min(10, bookedFlight.getFreeSeats()))));

        PopulateComboBoxes();

        distance = DistanceCalculator.Distance(bookedFlight.getFromAirport().getLatitude(),
                bookedFlight.getFromAirport().getLongitude(),
                bookedFlight.getToAirport().getLatitude(),
                bookedFlight.getToAirport().getLongitude());

        totalSum = (int) distance * 80 + 4999;
        totalSumLabel.setText(totalSum + " Ft");

        ticketSpinner.valueProperty().addListener(observable -> {
            CalculateBookingPrice();
        });

        categoryComboBox.valueProperty().addListener(observableValue -> {
            CalculateBookingPrice();
        });

        travelClassComboBox.valueProperty().addListener(observable -> {
            CalculateBookingPrice();
        });

        FieldValidator();
        CheckCheckBox();
    }

    private void PopulateComboBoxes() {
        // region GetAll
        var payments = PaymentController.getInstance().getAll();
        var categories = CategoryController.getInstance().getAll();
        var travelClasses = TravelClassController.getInstance().getAll();
        // endregion

        //region ObservableList
        ObservableList<Payment> obsPaymentList = FXCollections.observableList(payments);
        ObservableList<Category> obsCategoryList = FXCollections.observableList(categories);
        ObservableList<TravelClass> obsTravelClassList = FXCollections.observableList(travelClasses);
        // endregion

        // region AddAll
        categoryComboBox.getItems().addAll(obsCategoryList);
        travelClassComboBox.getItems().addAll(obsTravelClassList);
        paymentComboBox.getItems().addAll(obsPaymentList);
        // endregion

        // region Callback
        Callback<ListView<Category>, ListCell<Category>> categoryFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        Callback<ListView<TravelClass>, ListCell<TravelClass>> travelClassFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(TravelClass item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        Callback<ListView<Payment>, ListCell<Payment>> paymentFactory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Payment item, boolean empty){
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        // endregion

        //region Display
        categoryComboBox.setCellFactory(categoryFactory);
        categoryComboBox.setButtonCell(categoryFactory.call(null));

        travelClassComboBox.setCellFactory(travelClassFactory);
        travelClassComboBox.setButtonCell(travelClassFactory.call(null));

        paymentComboBox.setCellFactory(paymentFactory);
        paymentComboBox.setButtonCell(paymentFactory.call(null));
        //endregion
    }

    private void CalculateBookingPrice() {
        var category = categoryComboBox.getSelectionModel().getSelectedItem();
        var travelClass = travelClassComboBox.getSelectionModel().getSelectedItem();

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

        totalSum = (int) (distance * 80
                + (ticketSpinner.getValue() * ticketPrice * luxury));

        totalSumLabel.textProperty().bind(new SimpleStringProperty(totalSum + " Ft"));
    }

    private void CheckCheckBox() {
        sameTickets.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (sameTickets.isSelected()) {
                ticketSelector.setVisible(false);
                bookingButton.setVisible(true);
                categoryComboBox.setVisible(true);
                travelClassComboBox.setVisible(true);
            } else {
                ticketSelector.setVisible(true);
                bookingButton.setVisible(false);
                categoryComboBox.setVisible(false);
                travelClassComboBox.setVisible(false);
            }
        });
    }

    public static int getCountOfTicket() {
        return countOfTicket;
    }

    public static double getDistance() {
        return distance;
    }

    private void PopulateBooking() {
        var bookings = BookingController.getInstance().getAll();
        var maxId = 0;
        for (var booking: bookings) {
            if(booking.getId() > maxId) {
                maxId = booking.getId();
            }
        }
        maxId++;

        booking.setId(maxId);
        booking.setUser(LoginUserController.getUser());
        booking.setPayment(paymentComboBox.getSelectionModel().getSelectedItem());
        booking.setFlight(bookedFlight);
    }

    private void PopulateTicket() {
        ticket.setCategory(categoryComboBox.getSelectionModel().getSelectedItem());
        ticket.setBooking(booking);
        ticket.setTravelClass(travelClassComboBox.getSelectionModel().getSelectedItem());
    }

    private void FieldValidator() {
        ticketSelector.disableProperty().bind(paymentComboBox.valueProperty().isNull());

        bookingButton.disableProperty().bind(paymentComboBox.valueProperty().isNull()
                .or(categoryComboBox.valueProperty().isNull())
                .or(travelClassComboBox.valueProperty().isNull()));
    }

    public void addTicket(ActionEvent actionEvent) {
        countOfTicket = ticketSpinner.getValue();
        PopulateBooking();

        try {
            App.DialogDeliver("add_ticket.fxml","Foglalás", false);
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a jegyfoglalás ablakot");
        }
    }

    public void saveBooking(ActionEvent event) {
        countOfTicket = ticketSpinner.getValue();

        PopulateBooking();
        PopulateTicket();

        if (BookingController.getInstance().add(booking)) {
            for(int i = 0; i < countOfTicket; i++) {
                TicketController.getInstance().add(ticket);
            }

            Utils.showInformation("Sikeres foglalás");
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.close();
        } else {
            Utils.showWarning("Nem sikerült menteni az új repülőgépet");
        }
    }

    public void showHotels(ActionEvent actionEvent) {
        Stage hotelStage = new Stage();
        Parent root = null;

        try {
            root = FXMLLoader.load(App.class.getResource("/fxmlView/userView/hotel_list.fxml"));
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült megnyitni a szállodák listáját");
            e.printStackTrace();
        }

        var scene = new Scene(root);

        hotelStage.getIcons().add(new Image(App.class.getResourceAsStream("/pictures/icon.jpg")));
        hotelStage.setScene(scene);
        hotelStage.setTitle("Szállodák");
        hotelStage.show();
    }

    public static Booking getBooking() {
        return booking;
    }
}
