package hu.adatb.controller;

import hu.adatb.dao.BookingDao;
import hu.adatb.dao.BookingDaoImpl;
import hu.adatb.dao.FlightDao;
import hu.adatb.dao.FlightDaoImpl;
import hu.adatb.model.Booking;
import hu.adatb.model.Flight;

import java.util.List;

public class BookingController {

    private BookingDao dao = new BookingDaoImpl();
    private static BookingController single_instance = null;


    public static BookingController getInstance(){
        if(single_instance == null){
            single_instance = new BookingController();
        }
        return single_instance;
    }

    public boolean add(Booking booking) {
        return dao.add(booking);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Booking> getAll() {
        return dao.getAll();
    }
}
