package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.controller.UserController;
import hu.adatb.model.User;
import hu.adatb.utils.Utils;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegOrLoginController implements Initializable {

    private static List<User> users;

    @FXML
    public void registration() {
        try {
            System.out.println("\n" + App.CurrentTime() + "Destroyed first page");
            AddUserController.setIsAdmin(false);
            App.StageDeliver("add_user.fxml", "Regisztráció", "style.css");
            System.out.println(App.CurrentTime() + "Opened registration page");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open registration page");
            Utils.showWarning("Nem sikerült megnyitni a regisztrációs ablakot");
            e.printStackTrace();
        }
    }

    @FXML
    public void login() {
        try {
            System.out.println("\n" + App.CurrentTime() + "Destroyed first page");
            App.StageDeliver("login_user.fxml", "Bejelentkezés");
            System.out.println(App.CurrentTime() + "Opened login page");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open login page");
            Utils.showWarning("Nem sikerült megnyitni a bejelentekző ablakot");
            e.printStackTrace();
        }
    }

    public static void back(String from) {
        try {
            System.out.println("\n" + App.CurrentTime() + "Destroyed " + from + " page");
            App.StageDeliver("reg_or_login.fxml", "Repülőjárat foglaló rendszer", "style.css");
            System.out.println(App.CurrentTime() + "Opened first page");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open first page");
            Utils.showWarning("Nem sikerült megnyitni a főablakot ablakot");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = UserController.getInstance().getAll();
    }

    public static List<User> getAllUsers() {
        System.out.println(App.CurrentTime() + "Get " + users.size() + " users");
        return users;
    }
}
