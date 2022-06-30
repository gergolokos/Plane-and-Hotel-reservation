package hu.adatb.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class MainFxmlLoader {
    private static Pane view;

    public static Pane getPage(String fileName, boolean isAdminView) {
        String filePath = "/fxmlView/";

        if (isAdminView) {
            filePath += "adminView/";
        } else {
            filePath += "userView/";
        }

        try {
            view = FXMLLoader.load(MainFxmlLoader.class.getResource(filePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
