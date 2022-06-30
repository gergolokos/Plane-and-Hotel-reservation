package hu.adatb.controller;

import hu.adatb.dao.AlertDao;
import hu.adatb.dao.AlertDaoImpl;
import hu.adatb.model.Alert;

import java.util.List;

public class AlertController {
    private AlertDao dao = new AlertDaoImpl();
    private static AlertController single_instance = null;


    public static AlertController getInstance(){
        if(single_instance == null){
            single_instance = new AlertController();
        }
        return single_instance;
    }

    public boolean add(Alert alert) {
        return dao.add(alert);
    }

    public boolean delete(int id) {
        return dao.delete(id);
    }

    public List<Alert> getAll() {
        return dao.getAll();
    }
}
