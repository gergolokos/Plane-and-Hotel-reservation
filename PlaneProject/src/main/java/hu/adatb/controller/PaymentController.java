package hu.adatb.controller;

import hu.adatb.dao.PaymentDao;
import hu.adatb.dao.PaymentDaoImpl;
import hu.adatb.model.Payment;

import java.util.List;

public class PaymentController {
    private PaymentDao dao = new PaymentDaoImpl();
    private static PaymentController single_instance = null;


    public static PaymentController getInstance(){
        if(single_instance == null){
            single_instance = new PaymentController();
        }
        return single_instance;
    }

    public List<Payment> getAll() {
        return dao.getAll();
    }
}
