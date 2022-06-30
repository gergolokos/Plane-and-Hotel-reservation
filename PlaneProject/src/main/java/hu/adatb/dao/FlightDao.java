package hu.adatb.dao;

import hu.adatb.model.Flight;

import java.util.List;

public interface FlightDao {

    public boolean add (Flight flight);

    public boolean delete (int id);

    public List<Flight> getAll();

    boolean update(Flight flight);
}
