package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Airport;
import hu.adatb.model.City;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class AirportDaoImpl implements AirportDao {

    @Override
    public boolean add(Airport airport) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_AIRPORT)){

            st.setString(1, airport.getName());
            st.setDouble(2, airport.getLatitude());
            st.setDouble(3, airport.getLongitude());
            st.setInt(4, airport.getCity().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful addition");
                Utils.showInformation("Sikeres hozzáadás");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed addition");
        }

        Utils.showWarning("Nem sikerült a reptér hozzáadás");
        return false;
    }

    @Override
    public boolean update(Airport airport) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(UPDATE_AIRPORT)) {

            st.setString(1, airport.getName());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful update");
                Utils.showInformation("Sikeres módosítás");
                return true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed update airport");
        }

        Utils.showWarning("Nem sikerült a módosítás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_AIRPORT)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted airport with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete airport");
        }

        return false;
    }

    @Override
    public List<Airport> getAll() {
        List<Airport> result = new ArrayList<>();

        try(Connection conn = Database.ConnectionToDatabase();
            Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(SELECT_AIRPORT);

            while (rs.next()) {
                var cityId = rs.getInt("varos_id");
                var city = GetById.GetCityById(cityId);

                if (city == null) {
                    throw new GetByIdException("Cannot get city name by id");
                }

                Airport airport = new Airport(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getDouble("szelesseg"),
                        rs.getDouble("hosszusag"),
                        city
                );

                result.add(airport);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a repülőtereket");
        }


        return result;
    }
}
