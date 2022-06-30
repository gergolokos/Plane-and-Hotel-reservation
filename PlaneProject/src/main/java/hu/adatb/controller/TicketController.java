package hu.adatb.controller;

import hu.adatb.dao.TicketDao;
import hu.adatb.dao.TicketDaoImpl;
import hu.adatb.model.Ticket;

import java.util.List;

public class TicketController {
    private TicketDao dao = new TicketDaoImpl();
    private static TicketController single_instance = null;


    public static TicketController getInstance(){
        if(single_instance == null){
            single_instance = new TicketController();
        }
        return single_instance;
    }

    public boolean add(Ticket ticket) {
        return dao.add(ticket);
    }

    public List<Ticket> getAll() {
        return dao.getAll();
    }
}
