package hu.adatb.dao;

import hu.adatb.App;
import hu.adatb.model.User;
import hu.adatb.query.Database;
import hu.adatb.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hu.adatb.query.Queries.*;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean add(User user) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(INSERT_USER)){

            st.setString(1, user.getName());
            st.setString(2, user.getPassword());
            st.setInt(3, user.isAdmin() ? 1 : 0);
            st.setString(4, user.getEmail());

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Successful registration");
                Utils.showInformation("Sikeres regisztráció");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed registration");
        }

        Utils.showWarning("Nem sikerült a regisztráció");
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = Database.ConnectionToDatabase();
             PreparedStatement st = conn.prepareStatement(DELETE_USER)){

            st.setInt(1, id);

            int res = st.executeUpdate();

            if (res == 1) {
                System.out.println(App.CurrentTime() + "Deleted user with " + id + " id");
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(App.CurrentTime() + "Failed delete user");
        }

        return false;
    }

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();

        try (Connection conn = Database.ConnectionToDatabase();
             Statement stmt = conn.createStatement()){

            ResultSet rs = stmt.executeQuery(SELECT_USER);

            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("felh_nev"),
                        rs.getString("jelszo"),
                        rs.getInt("isAdmin") == 1,
                        rs.getString("email")
                );

                result.add(user);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Utils.showWarning("Nem sikerült lekérni a felhasználót");
        }

        return result;
    }
}
