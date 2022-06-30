package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Alert;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class AlertDaoImpl implements AlertDao {

    @Override
    public boolean add(Alert alert) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_ALERT)){

            st.setString(1, alert.getMessage());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful addition");
                Utils.showInformation("Sikeres hozzáadás");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed addition");
        }

        Utils.showWarning("Nem sikerült a hozzáadás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_ALERT)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted alert with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete alert");
        }

        return false;
    }

    @Override
    public List<Alert> getAll() {
        List<Alert> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_ALERT);

            while (rs.next()) {
                Alert alert = new Alert(
                        rs.getInt("id"),
                        rs.getString("uzenet")
                );

                result.add(alert);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a figyelmeztetéseket");
        }

        return result;
    }
}
