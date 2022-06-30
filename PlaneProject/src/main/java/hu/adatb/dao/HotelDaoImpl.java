package hu.adatb.dao;

import hu.adatb.App;

import hu.adatb.model.Hotel;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class HotelDaoImpl implements HotelDao{

    @Override
    public boolean add(Hotel hotel) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_HOTEL)){

            st.setString(1, hotel.getName());
            st.setDouble(2, hotel.getStars());
            st.setInt(3, hotel.getCity().getId());

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
    public boolean update(Hotel hotel) {
        try(Connection conn = Database.ConnectionToDatabase();
            PreparedStatement st = conn.prepareStatement(UPDATE_HOTEL)) {

            st.setString(1, hotel.getName());
            st.setInt(2, hotel.getStars());
            st.setInt(3, hotel.getCity().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful update");
                Utils.showInformation("Sikeres módosítás");
                return true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed update hotel");
        }

        Utils.showWarning("Nem sikerült a módosítás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_HOTEL)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted hotel with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete hotel");
        }

        return false;
    }

    @Override
    public List<Hotel> getAll() {
        List<Hotel> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_HOTEL);

            while (rs.next()) {
                var cityId = rs.getInt("varos_id");
                var city = GetById.GetCityById(cityId);

                if (city == null) {
                    throw new GetByIdException("Cannot get city name by id");
                }

                Hotel hotel = new Hotel(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("csillagok_szama"),
                        city
                );

                result.add(hotel);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a szállodákat");
        }

        return result;
    }


}
