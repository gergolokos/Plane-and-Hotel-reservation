package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.*;
import hu.adatb.query.Database;
import hu.adatb.utils.GetById;
import hu.adatb.utils.GetByIdException;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class BookingDaoImpl implements BookingDao {
    @Override
    public boolean add(Booking booking) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_BOOKING)){

            st.setInt(1, booking.getUser().getId());
            st.setInt(2, booking.getFlight().getId());
            st.setInt(3, booking.getPayment().getId());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful booking");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed booking");
        }

        Utils.showWarning("Nem sikerült a foglalás");
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_BOOKING)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted booking with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete booking");
        }

        return false;
    }

    @Override
    public List<Booking> getAll() {
        List<Booking> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_BOOKING);

            while (rs.next()) {
                var userId = rs.getInt("felh_id");
                var flightId = rs.getInt("jarat_id");
                var paymentId = rs.getInt("fizetesi_mod_id");

                User user = GetById.GetUserById(userId);
                Flight flight = GetById.GetFlightById(flightId);
                Payment payment = GetById.GetPaymentById(paymentId);

                if (user == null || flight == null || payment == null) {
                    throw new GetByIdException("Cannot get booking (because of user, flight or payment listing by id)");
                }

                Booking booking = new Booking(
                        rs.getInt("id"),
                        user,
                        flight,
                        payment
                );

                result.add(booking);
            }

        } catch (SQLException | ClassNotFoundException | GetByIdException e) {
            Utils.showWarning("Nem sikerült lekérni a foglalásokat");
        }

        return result;
    }
}
