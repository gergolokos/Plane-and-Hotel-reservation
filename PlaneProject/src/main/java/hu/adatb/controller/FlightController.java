package hu.adatb.controller;

import hu.adatb.dao.FlightDao;
import hu.adatb.dao.FlightDaoImpl;
import hu.adatb.model.Flight;

import java.util.List;

public class FlightController {

    private FlightDao dao = new FlightDaoImpl();
    private static FlightController single_instance = null;


    public static FlightController getInstance(){
        if(single_instance == null){
            single_instance = new FlightController();
        }
        return single_instance;
    }

    public boolean add(Flight flight) {
        return dao.add(flight);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Flight> getAll() {
        return dao.getAll();
    }

    public boolean update(Flight flight) {
        return dao.update( flight);
    }
}
