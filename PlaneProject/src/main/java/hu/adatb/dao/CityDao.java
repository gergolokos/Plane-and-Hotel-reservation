package hu.adatb.dao;

import hu.adatb.model.Airport;
import hu.adatb.model.City;

import java.util.List;

public interface CityDao {

    public boolean add (City city);

    public boolean update (City city);

    public boolean delete (int id);

    public List<City> getAll();
}
