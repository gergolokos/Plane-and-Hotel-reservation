package hu.adatb.view.controller;

import hu.adatb.controller.UserController;
import hu.adatb.model.User;
import hu.adatb.utils.Utils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddUserController implements Initializable {

    @FXML
    TextField nameField;

    @FXML
    TextField emailField;

    @FXML
    PasswordField passwordField;

    @FXML
    ImageView saveButton;

    @FXML
    Label errorMsgName;

    @FXML
    Label errorMsgEmail;

    @FXML
    Label errorMsgPassword;

    private List<User> users;
    public static boolean isAdmin;

    public AddUserController() {
    }

    private User user = new User();

    @FXML
    private void save (MouseEvent mouseEvent) {
        if (UserController.getInstance().add(user) && !isAdmin) {
            RegOrLoginController.back("registration");
        } else {
            return;
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        RegOrLoginController.back("registration");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = RegOrLoginController.getAllUsers();

        user.nameProperty().bind(nameField.textProperty());
        user.passwordProperty().bind(passwordField.textProperty());
        user.emailProperty().bind(emailField.textProperty());
        user.setAdmin(isAdmin);

        FieldValidator();
    }

    private void FieldValidator() {
        saveButton.disableProperty().bind(nameField.textProperty().isEmpty()
                .or(emailField.textProperty().isEmpty())
                .or(passwordField.textProperty().isEmpty()
                .or(errorMsgName.textProperty().isNotEmpty())
                .or(errorMsgEmail.textProperty().isNotEmpty())
                .or(errorMsgPassword.textProperty().isNotEmpty())));

        nameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            var match = false;
            for (var user: users) {
                if (newValue.equals(user.getName())) {
                    match = true;
                }
            }

            if (!match) {
                errorMsgName.setText("");
                FieldValidator();
            } else {
                errorMsgName.setText("Ilyen név már létezik");
            }
        });

        emailField.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue.matches("\\S+@\\S+\\.\\S\\S") ||
                    newValue.matches("\\S+@\\S+\\.\\S\\S\\S")) {
                errorMsgEmail.setText("");
                FieldValidator();
            } else {
                errorMsgEmail.setText("Érvénytelen email cím");
            }
        });

        passwordField.textProperty().addListener((observableValue, oldValue, newValue) -> {

            if (newValue.length() > 4) {
                errorMsgPassword.setText("");
                FieldValidator();
            } else {
                errorMsgPassword.setText("Túl rövid a jelszó");
            }
        });
    }

    public static void setIsAdmin(boolean isAdmin) {
        AddUserController.isAdmin = isAdmin;
    }
}
