package controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import main.App;
import main.Main;
import views.Views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public HBox root;
    public TextField email;
    public TextField telefon;
    public TextField imie;
    public TextField nazwisko;
    public TextField login;
    public PasswordField pass;


    public void zarejestruj() {
        //sprawdzenie zadne pole nie jest puste
        if(email.getText().isEmpty() || telefon.getText().isEmpty() ||
                imie.getText().isEmpty() || nazwisko.getText().isEmpty() ||
                login.getText().isEmpty() || pass.getText().isEmpty()){
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Prosze uzupełnić puste pola");
            alertErr.initOwner(root.getScene().getWindow());
            alertErr.showAndWait();
        }else {//pola nie sa puste
            if(App.getInstance().getDbHandler().registerUser(login.getText(), pass.getText(), imie.getText(), nazwisko.getText(), telefon.getText(), email.getText())){
                //metoda registerUser zwrocila true
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Rejestracja pomyślna.");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Konto zostało utworzone.");
                alertInfo.initOwner(root.getScene().getWindow());
                alertInfo.showAndWait();
                Main.setView(Views.LOGIN);
            }else {//zwrocila false
                Alert alertErr = new Alert(Alert.AlertType.ERROR);
                alertErr.setTitle("Błąd");
                alertErr.setHeaderText(null);
                alertErr.setContentText("Rejestracja nie powiodła się");
                alertErr.initOwner(root.getScene().getWindow());
                alertErr.showAndWait();
            }
        }
    }

    //zmienia okno na okno domowe
    public void powrot() {
        Main.setView(Views.HOME);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> imie.requestFocus());
    }
}
