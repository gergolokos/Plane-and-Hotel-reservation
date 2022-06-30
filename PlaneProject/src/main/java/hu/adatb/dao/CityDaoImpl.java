package hu.adatb.dao;

import hu.adatb.App;
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

public class CityDaoImpl implements CityDao {

    @Override
    public boolean add(City city) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_CITY)){

            st.setString(1, city.getName());
            st.setInt(2, city.getCountry().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful city saving");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed city saving");
        }

        Utils.showWarning("Nem sikerült a város hozzáadása");
        return false;
    }

    @Override
    public boolean update(City city) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(UPDATE_CITY)) {

            st.setString(1, city.getName());
            st.setInt(2, city.getCountry().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful update");
                Utils.showInformation("Sikeres módosítás");
                return true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed update plane");
        }

        Utils.showWarning("Nem sikerült a módosítás");
        return false;
    }


    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_CITY)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted city with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete city");
        }

        return false;
    }

    @Override
    public List<City> getAll() {
        List<City> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_CITY);

            while (rs.next()) {
                var orszagId = rs.getInt("orszag_id");
                var orszag = GetById.GetCountryById(orszagId);

                if (orszag == null) {
                    throw new GetByIdException("Cannot get country name by id");
                }

                City city = new City(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        orszag
                );

                result.add(city);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a városokat");
        }

        return result;
    }
}
