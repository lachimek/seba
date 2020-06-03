package controllers;

import javafx.scene.layout.AnchorPane;
import main.Main;
import views.Views;

import java.io.IOException;

public class HomeController {
    public AnchorPane root;

    //metoda przycisku ktory zmienia okno na okno logowania
    public void zaloguj() throws IOException {
        Main.setView(Views.LOGIN);
    }

    //metoda przycisku ktory zmienia okno na okno rejestracji
    public void zarejestruj() throws IOException {
        Main.setView(Views.REJESTRACJA);
    }
}
