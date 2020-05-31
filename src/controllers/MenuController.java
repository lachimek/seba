package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    public AnchorPane root;

    public void archiwum() {
    }

    public void zarzadzaj() {
    }

    public void aktywneZlecenia() {
    }

    public void dodajZlecenie() {
    }

    public void wyloguj() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Strona_domowa_interface.fxml")));
        root.getChildren().setAll(anchorPane);
    }
}
