package hu.adatb.controller;

import hu.adatb.dao.TravelClassDao;
import hu.adatb.dao.TravelClassDaoImpl;
import hu.adatb.model.TravelClass;

import java.util.List;

public class TravelClassController {
    private TravelClassDao dao = new TravelClassDaoImpl();
    private static TravelClassController single_instance = null;


    public static TravelClassController getInstance(){
        if(single_instance == null){
            single_instance = new TravelClassController();
        }
        return single_instance;
    }

    public List<TravelClass> getAll() {
        return dao.getAll();
    }
}
