package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.Flight;
import hu.adatb.model.Plane;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class FlightDaoImpl implements FlightDao {

    @Override
    public boolean add(Flight flight) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_FLIGHT)){

            st.setString(1, flight.getDateTimeInRightFormat());
            st.setInt(2, flight.getFromAirport().getId());
            st.setInt(3, flight.getToAirport().getId());
            st.setInt(4, flight.getPlane().getId());
            st.setInt(5, flight.getFreeSeats());

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
             PreparedStatement st = conn.prepareStatement(DELETE_FLIGHT)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted flight with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete flight");
        }

        return false;
    }

    @Override
    public List<Flight> getAll() {
        List<Flight> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_FLIGHT);

            while (rs.next()) {
                var date = rs.getDate("felszallas_datum").toLocalDate();
                var time = rs.getTime("felszallas_datum").toLocalTime();

                LocalDateTime dateTime = LocalDateTime.of(date, time);

                var planeId = rs.getInt("repulogep_id");
                var plane = GetById.GetPlaneById(planeId);

                var fromAirportId = rs.getInt("repuloter_id_fel");
                var fromAirport = GetById.GetAirportById(fromAirportId);

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                var toAirportId = rs.getInt("repuloter_id_le");
                var toAirport = GetById.GetAirportById(toAirportId);

                Flight flight = new Flight(
                        rs.getInt("id"),
                        dateTime,
                        fromAirport,
                        toAirport,
                        plane,
                        rs.getInt("szabad_helyek")
                );

                result.add(flight);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a járatokat");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public boolean update(Flight flight) {
        return false;
    }
}
