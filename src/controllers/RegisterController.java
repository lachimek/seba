package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public AnchorPane root;
    public TextField email;
    public TextField telefon;
    public TextField imie;
    public TextField nazwisko;
    public TextField login;
    public PasswordField pass;


    public void zarejestruj() throws IOException {
        if(email.getText().isEmpty() || telefon.getText().isEmpty() ||
                imie.getText().isEmpty() || nazwisko.getText().isEmpty() ||
                login.getText().isEmpty() || pass.getText().isEmpty()){
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Prosze uzupełnić puste pola");
            alertErr.initOwner(root.getScene().getWindow());
            alertErr.showAndWait();
        }else {
            //todo rejestracja w bazie
            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("Rejestracja pomyślna.");
            alertInfo.setHeaderText(null);
            alertInfo.setContentText("Konto zostało utworzone.");
            alertInfo.initOwner(root.getScene().getWindow());
            alertInfo.showAndWait();
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Logowanie_interface.fxml")));
            root.getChildren().setAll(anchorPane);
        }
    }

    public void powrot() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Strona_domowa_interface.fxml")));
        root.getChildren().setAll(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> imie.requestFocus());
    }
}
