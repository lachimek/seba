package controllers;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import main.App;
import main.Main;
import views.Views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//kontroler okna logowania implementujacy interfejs Initializable
public class LoginController implements Initializable {
    public PasswordField pass;
    public TextField login;
    public AnchorPane root;

    //metoda przycisku ktory zmienia okno na okno rejestracji
    public void register() throws IOException {
        Main.setView(Views.REJESTRACJA);
    }
    //metoda przycisku ktory zmienia okno na okno logowania
    public void login() throws IOException {
        if(App.getInstance().getDbHandler().loginUser(login.getText(), pass.getText())){
            Main.setView(Views.MENU);
        }else{
            //popup typu error informujacy o blednych danych
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText(null);
            alert.setContentText("Błędne dane logowania");
            alert.initOwner(root.getScene().getWindow());
            alert.showAndWait();
        }
    }

    //metoda zaimplementowana z interfejsu Initializable ktora wywoluje sie po wygenerowaniu okna
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> login.requestFocus());
    }
}
