package hu.adatb.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Utils {
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Biztos törölni akarsz?", ButtonType.YES, ButtonType.NO);
        alert.initStyle(StageStyle.UNDECORATED);

        Optional<ButtonType> type = alert.showAndWait();

        return type;
    }
}
