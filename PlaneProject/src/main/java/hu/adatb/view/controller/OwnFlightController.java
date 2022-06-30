package hu.adatb.view.controller;

import hu.adatb.controller.AirportController;
import hu.adatb.controller.BookingController;
import hu.adatb.controller.FlightController;
import hu.adatb.model.Airport;
import hu.adatb.model.Booking;
import hu.adatb.model.Flight;
import hu.adatb.model.Ticket;
import hu.adatb.utils.DistanceCalculator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class OwnFlightController implements Initializable {

    @FXML
    public TableView<Flight> table;

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
    private TableColumn<Booking, Integer> ticketCol;

    @FXML
    private TableColumn<Flight, Void> actionCol;

    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Airport> airports;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookings = BookingController.getInstance().getAll();
        flights = FlightController.getInstance().getAll();
        airports = AirportController.getInstance().getAll();

        var filteredBookingsByUser = bookings.stream().filter(booking -> booking.getUser().getId() == LoginUserController.getUser().getId()).collect(Collectors.toList());
        List<Flight> filteredFlights = new ArrayList<>();

        ArrayList<Integer> flightIds = new ArrayList<>();

        for (var booking: filteredBookingsByUser) {
            if(!flightIds.contains(booking.getFlight().getId())) {
                filteredFlights.add(flights.stream().filter(flight -> flight.getId() == booking.getFlight().getId()).collect(Collectors.toList()).get(0));
                flightIds.add(booking.getFlight().getId());
            }

        }

        table.setItems(FXCollections.observableList(filteredFlights));

        PopulateTable();
    }

    private void PopulateTable(){

        fromCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getFromAirport().getName()));
        toCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getToAirport().getName()));
        whenCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getDateTimeInRightFormat()));
        timeCol.setCellValueFactory(
                __-> new SimpleStringProperty(__.getValue()
                        .getTravelTime(DistanceCalculator.GetLatitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getFromAirport().getName()),
                                DistanceCalculator.GetLatitudeByName(airports, __.getValue().getToAirport().getName()),
                                DistanceCalculator.GetLongitudeByName(airports, __.getValue().getToAirport().getName()),
                                __.getValue().getPlane())));
        withCol.setCellValueFactory(__-> new SimpleStringProperty(__.getValue().getPlane().getName()));
    }
}
