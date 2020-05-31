package controllers;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class HomeController {
    public AnchorPane root;

    public void zaloguj() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Logowanie_interface.fxml")));
        root.getChildren().setAll(anchorPane);
    }

    public void zarejestruj() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Rejestracja_interface.fxml")));
        root.getChildren().setAll(anchorPane);
    }
}
