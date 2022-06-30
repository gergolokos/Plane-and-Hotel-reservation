package hu.adatb.controller;

import hu.adatb.dao.CityDao;
import hu.adatb.dao.CityDaoImpl;
import hu.adatb.model.Airport;
import hu.adatb.model.City;

import java.util.List;

public class CityController {
    private CityDao dao = new CityDaoImpl();
    private static CityController single_instance = null;


    public static CityController getInstance(){
        if(single_instance == null){
            single_instance = new CityController();
        }
        return single_instance;
    }

    public boolean add(City city) {
        return dao.add(city);
    }

    public boolean update(City city) {
        return dao.update( city);
    }


    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<City> getAll() {
        return dao.getAll();
    }
}
