package hu.adatb.dao;

import hu.adatb.model.Payment;

import java.util.List;

public interface PaymentDao {

    public List<Payment> getAll();
}
