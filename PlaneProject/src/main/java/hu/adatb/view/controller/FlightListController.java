package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.AirportController;
import hu.adatb.controller.FlightController;
import hu.adatb.controller.HotelController;
import hu.adatb.controller.PlaneController;
import hu.adatb.model.Airport;
import hu.adatb.model.Flight;
import hu.adatb.model.Hotel;
import hu.adatb.model.Plane;
import hu.adatb.utils.DistanceCalculator;
import hu.adatb.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FlightListController implements Initializable {

    @FXML
    ComboBox<String> fromAirport;

    @FXML
    ComboBox<String> toAirport;

    @FXML
    DatePicker dateBegin;

    @FXML
    DatePicker dateEnd;

    @FXML
    public TableView<Flight> table = new TableView<>();

    @FXML
    private TableColumn<Flight, String> fromCol;

    @FXML
    private TableColumn<Flight, String> toCol;

    @FXML
    private TableColumn<Flight, String> whenCol;

    @FXML
    private TableColumn<Flight, String> timeCol;

    @FXML
    private TableColumn<Flight, String> withCol;

    @FXML
    private TableColumn<Flight, Integer> seatCol;

    @FXML
    private TableColumn<Flight, String> hotelCol;

    @FXML
    private TableColumn<Flight, Void> actionCol;

    @FXML
    private Button searchButton;

    @FXML
    private Label infoText;

    private List<Flight> flights;
    private List<Flight> filteredFlights;
    private List<Airport> airports;
    private List<Hotel> hotels;
    public static Flight bookedFlight;
    public static String toAirportHotelNames;

    String selectedFromAirport;
    String selectedToAirport;
    LocalDate selectedDateBegin;
    LocalDate selectedDateEnd;

    public void refreshTable() {
        filteredFlights = flights.stream()
                .filter(flight-> flight.getFromAirport().getName().equals(selectedFromAirport)
                        && flight.getToAirport().getName().equals(selectedToAirport)
                        && ((flight.getDateTime().toLocalDate()).isAfter(selectedDateBegin)
                        || (flight.getDateTime().toLocalDate()).isEqual(selectedDateBegin))
                        && ((flight.getDateTime().toLocalDate()).isBefore(selectedDateEnd)
                        || (flight.getDateTime().toLocalDate()).isEqual(selectedDateEnd))
                        && flight.getFreeSeats() > 0
                ).collect(Collectors.toList());

        table.setItems(FXCollections.observableList(filteredFlights));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flights = FlightController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();
        hotels = HotelController.getInstance().getAll();

        for (var airport: airports) {
            fromAirport.getItems().addAll(airport.getName());
            toAirport.getItems().addAll(airport.getName());
        }

        searchButton.disableProperty().bind(fromAirport.valueProperty().isNull()
                .or(toAirport.valueProperty().isNull())
                .or(dateBegin.valueProperty().isNull())
                .or(dateEnd.valueProperty().isNull()));
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        selectedFromAirport = fromAirport.getSelectionModel().getSelectedItem();
        selectedToAirport = toAirport.getSelectionModel().getSelectedItem();
        selectedDateBegin = dateBegin.getValue();
        selectedDateEnd = dateEnd.getValue();

        refreshTable();

        fromCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFromAirport().getName()));
        toCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getToAirport().getName()));
        whenCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getDateTimeInRightFormat()));
        timeCol.setCellValueFactory(
                __-> new SimpleStringProperty(__.getValue()
                        .getTravelTime(DistanceCalculator.GetLatitudeByName(airports, selectedFromAirport),
                                DistanceCalculator.GetLongitudeByName(airports, selectedFromAirport),
                                DistanceCalculator.GetLatitudeByName(airports, selectedToAirport),
                                DistanceCalculator.GetLongitudeByName(airports, selectedToAirport),
                                        __.getValue().getPlane())));
        withCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getPlane().getName()));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        hotelCol.setCellValueFactory(__-> new SimpleStringProperty(
                __.getValue().getHotels(__.getValue().getToAirport().getCity().getName())));

        actionCol.setCellFactory(param ->
                new TableCell<>(){

                    final Button bookingButton = new Button("Foglalás");

                    {
                        bookingButton.setOnAction(event -> {
                            bookedFlight = table.getItems().get(getIndex());
                            toAirportHotelNames = bookedFlight.getHotels(bookedFlight.getToAirport().getCity().getName());

                            try {
                                App.DialogDeliver("add_booking.fxml","Foglalás", false);
                            } catch (IOException e) {
                                Utils.showWarning("Nem sikerült megnyitni a foglalás ablakot");
                            }

                            refreshTable();
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(bookingButton);
                        }
                    }
                }
        );

        if (filteredFlights.size() > 0) {
            table.setVisible(true);
            infoText.setVisible(false);
        } else {
            table.setVisible(false);
            infoText.setVisible(true);
            infoText.setText("Nincs a szűrőknek megfelelő járat");
        }
    }

    public static Flight getBookedFlight() {
        return bookedFlight;
    }

    public static String getToAirportHotelNames() {
        return toAirportHotelNames;
    }
}
