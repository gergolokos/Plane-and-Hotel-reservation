package hu.adatb.controller;

import hu.adatb.dao.CountryDao;
import hu.adatb.dao.CountryDaoImpl;
import hu.adatb.model.Country;

import java.util.List;

public class CountryController {
    private CountryDao dao = new CountryDaoImpl();
    private static CountryController single_instance = null;


    public static CountryController getInstance(){
        if(single_instance == null){
            single_instance = new CountryController();
        }
        return single_instance;
    }

    public boolean add(Country country) {
        return dao.add(country);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Country> getAll() {
        return dao.getAll();
    }
}
