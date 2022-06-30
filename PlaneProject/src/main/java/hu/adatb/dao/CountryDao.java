package hu.adatb.dao;

import hu.adatb.model.Country;

import java.util.List;

public interface CountryDao {

    public boolean add (Country country);

    public boolean delete (int id);

    public List<Country> getAll();

}
