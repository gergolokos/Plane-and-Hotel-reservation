package hu.adatb.model;

import hu.adatb.utils.DistanceCalculator;
import javafx.beans.property.*;

public class Airport {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty latitude = new SimpleDoubleProperty();
    private DoubleProperty longitude = new SimpleDoubleProperty();
    private City city;

    public Airport(int id ,String name, double lat, double lon, City city)
    {
        this.id.set(id);
        this.name.set(name);
        this.latitude.set(lat);
        this.longitude.set(lon);
        this.city = city;
    }

    public Airport() {

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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getLatitude() {
        return latitude.get();
    }

    public DoubleProperty latitudeProperty() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude.set(latitude);
    }

    public double getLongitude() {
        return longitude.get();
    }

    public DoubleProperty longitudeProperty() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude.set(longitude);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
