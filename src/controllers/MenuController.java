package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.App;
import main.Main;
import views.Views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public AnchorPane root;
    public Label loggedInLbl;

    private App app = App.getInstance();


    public void archiwum() {
        app.archiwum = 1;
        Main.setView(Views.ZLECENIA);
    }

    public void aktywneZlecenia() {
        app.archiwum = 0;
        Main.setView(Views.ZLECENIA);
    }

    public void zarzadzaj() {
    }

    public void dodajZlecenie() {
    }

    public void wyloguj() {
        Main.setView(Views.HOME);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loggedInLbl.setText("Witaj "+ app.loggedInUser +"!");
    }
}
