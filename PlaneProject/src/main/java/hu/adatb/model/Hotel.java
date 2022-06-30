package hu.adatb.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Hotel {

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private IntegerProperty stars = new SimpleIntegerProperty();
    private City city;

    public Hotel(int id, String name, int stars, City city) {
        this.id.set(id);
        this.name.set(name);
        this.stars.set(stars);
        this.city = city;
    }

    public Hotel() {
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

    public int getStars() {
        return stars.get();
    }

    public IntegerProperty starsProperty() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars.set(stars);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
