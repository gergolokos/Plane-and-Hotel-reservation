package hu.adatb.dao;

import hu.adatb.model.Ticket;

import java.util.List;

public interface TicketDao {

    public boolean add(Ticket ticket);

    public boolean delete(int id);

    public List<Ticket> getAll();
}
