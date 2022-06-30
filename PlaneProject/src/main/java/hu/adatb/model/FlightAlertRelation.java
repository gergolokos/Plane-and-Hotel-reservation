package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FlightAlertRelation {

    private IntegerProperty id = new SimpleIntegerProperty();
    Flight flight;
    Alert alert;

    public FlightAlertRelation(int id, Flight flight, Alert alert) {
        this.id.set(id);
        this.flight = flight;
        this.alert = alert;
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

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }
}
