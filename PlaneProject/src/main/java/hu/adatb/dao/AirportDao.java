package hu.adatb.dao;

import hu.adatb.model.Airport;
import hu.adatb.model.Plane;

import java.util.List;

public interface AirportDao {

    public boolean add(Airport airport);

    public boolean update (Airport airport);

    public boolean delete(int id);

    public List<Airport> getAll();
}
