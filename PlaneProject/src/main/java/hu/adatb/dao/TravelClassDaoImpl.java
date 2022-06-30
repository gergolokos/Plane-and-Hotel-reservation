package hu.adatb.dao;

import hu.adatb.model.TravelClass;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.SELECT_TRAVEL_CLASS;

public class TravelClassDaoImpl implements TravelClassDao {
    @Override
    public List<TravelClass> getAll() {
        List<TravelClass> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_TRAVEL_CLASS);

            while (rs.next()) {
                TravelClass travelClass = new TravelClass(
                        rs.getInt("id"),
                        rs.getString("nev")
                );

                result.add(travelClass);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni az utazási osztályokat");
        }

        return result;
    }
}
