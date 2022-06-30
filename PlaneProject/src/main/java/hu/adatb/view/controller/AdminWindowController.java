package hu.adatb.view.controller;

import hu.adatb.App;
import hu.adatb.utils.MainFxmlLoader;
import hu.adatb.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminWindowController implements Initializable {

    @FXML
    private BorderPane mainPane;

    public AdminWindowController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public static void goToMainForAdmin() {
        try {
            System.out.println("\n"+ App.CurrentTime() + "Destroyed login page");
            Stage stage = App.StageDeliver("admin_main_window.fxml", "Főoldal");
            stage.setMaximized(true);
            System.out.println(App.CurrentTime() + "Opened admin main page after login");
        } catch (IOException e) {
            System.out.println(App.CurrentTime() + "Cannot open main page");
            Utils.showWarning("Nem sikerült megnyitni a főoldalt");
            e.printStackTrace();
        }
    }

    @FXML
    public void buttonAction1(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened Screen1");
        Pane view = MainFxmlLoader.getPage("Screen2.fxml", true);
        mainPane.setCenter(view);
    }



    @FXML
    public void getJaratokScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened flight screen");
        Pane view = MainFxmlLoader.getPage("FlightScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void getPlaneScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + " Opened plane screen");
        Pane view = MainFxmlLoader.getPage("PlaneScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void getAirportScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + " Opened airport screen");
        Pane view = MainFxmlLoader.getPage("AirportScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void getCityScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + " Opened city screen");
        Pane view = MainFxmlLoader.getPage("CityScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void getAlertScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened alert screen");
        Pane view = MainFxmlLoader.getPage("AlertScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void getHotelScreen(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened hotel screen");
        Pane view = MainFxmlLoader.getPage("HotelScreen.fxml", true);
        mainPane.setCenter(view);
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            Stage stage = App.StageDeliver("login_user.fxml", "Bejelentkezés");
            stage.setMaximized(false);
            stage.setWidth(600);
            stage.setHeight(400);
            System.out.println(App.CurrentTime() + "Destroyed admin page");
        } catch (IOException e) {
            Utils.showWarning("Nem sikerült a kijelentkezés");
            e.printStackTrace();
        }
    }

    @FXML
    public void regAdmin(ActionEvent actionEvent) {
        System.out.println(App.CurrentTime() + "Opened admin sign-up");
        AddUserController.setIsAdmin(true);
        Pane view = MainFxmlLoader.getPage("SignUpScreen.fxml", true);
        mainPane.setCenter(view);
    }

    public void navigateUserPage(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.close();
        UserWindowController.goToMainForUser();
    }
}
