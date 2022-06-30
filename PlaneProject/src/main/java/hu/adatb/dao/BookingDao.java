package hu.adatb.dao;

import hu.adatb.model.Booking;

import java.util.List;

public interface BookingDao {

    public boolean add(Booking booking);

    public boolean delete(int id);

    public List<Booking> getAll();
}
