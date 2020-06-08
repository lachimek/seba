package controllers;

import javafx.scene.layout.AnchorPane;
import main.Main;
import views.Views;


public class HomeController {
    public AnchorPane root;

    //metoda przycisku ktory zmienia okno na okno logowania
    public void zaloguj() {
        Main.setView(Views.LOGIN);
    }

    //metoda przycisku ktory zmienia okno na okno rejestracji
    public void zarejestruj() {
        Main.setView(Views.REJESTRACJA);
    }
}
