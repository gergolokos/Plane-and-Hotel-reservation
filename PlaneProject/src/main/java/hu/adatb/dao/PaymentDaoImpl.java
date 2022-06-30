package hu.adatb.dao;

import hu.adatb.model.Country;
import hu.adatb.model.Payment;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_PAYMENT;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public List<Payment> getAll() {
        List<Payment> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_PAYMENT);

            while (rs.next()) {
                Payment payment = new Payment(
                        rs.getInt("id"),
                        rs.getString("nev")
                );

                result.add(payment);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a fizetési módokat");
        }

        return result;
    }
}
