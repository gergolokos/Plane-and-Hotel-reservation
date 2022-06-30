package hu.adatb.model;

import hu.adatb.controller.HotelController;
import hu.adatb.utils.DistanceCalculator;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Flight {

    private IntegerProperty id = new SimpleIntegerProperty();
    private LocalDateTime dateTime;
    private Airport fromAirport;
    private Airport toAirport;
    private Plane plane;
    private IntegerProperty freeSeats = new SimpleIntegerProperty();

    public Flight(int id, LocalDateTime dateTime, Airport fromAirport, Airport toAirport, Plane plane, Integer freeSeats) {
        this.id.set(id);
        this.dateTime = dateTime;
        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.plane = plane;
        this.freeSeats.set(freeSeats);
    }

    public Flight() {
    }

    public String getDateTimeInRightFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return dateTime.format(formatter);
    }

    public String getTravelTime(double lat1, double lon1, double lat2, double lon2, Plane plane) {
        var distance = DistanceCalculator.Distance(lat1, lon1, lat2, lon2);
        var travelTimeInMinutes = (int)((distance/plane.getSpeed()) * 60);

        int hours = travelTimeInMinutes / 60;
        int minutes = travelTimeInMinutes - hours * 60;

        return hours > 0
                ? hours + " óra " + minutes + " perc"
                : travelTimeInMinutes + " perc";
    }

    public String getHotels(String toAirportCityName) {
        var hotels = HotelController.getInstance().getAll();
        var filteredHotels = hotels.stream().filter(hotel -> hotel.getCity().getName().equals(toAirportCityName)).collect(Collectors.toList());
        var allHotelsInCity = "";

        if (filteredHotels.size() == 0) {
            return "-";
        } else if (filteredHotels.size() == 1) {
            allHotelsInCity = filteredHotels.get(0).getName();
        } else {
            int i;
            for (i = 0; i < filteredHotels.size() - 1; i++) {
                allHotelsInCity += filteredHotels.get(i).getName() + ", ";
            }

            allHotelsInCity += filteredHotels.get(i).getName();
        }

        return allHotelsInCity;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(hu.adatb.model.Plane plane) {
        this.plane = plane;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Airport getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(Airport fromAirport) {
        this.fromAirport = fromAirport;
    }

    public Airport getToAirport() {
        return toAirport;
    }

    public void setToAirport(Airport toAirport) {
        this.toAirport = toAirport;
    }

    public int getFreeSeats() {
        return freeSeats.get();
    }

    public IntegerProperty freeSeatsProperty() {
        return freeSeats;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats.set(freeSeats);
    }
}
