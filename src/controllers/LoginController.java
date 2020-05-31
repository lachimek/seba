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

public class LoginController implements Initializable {
    public PasswordField pass;
    public TextField login;
    public AnchorPane root;


    public void register() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Rejestracja_interface.fxml")));
        root.getChildren().setAll(anchorPane);
    }

    public void login() throws IOException {
        if(pass.getText().equals("a") && login.getText().equals("a")){
            AnchorPane anchorPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/Menu_interface.fxml")));
            root.getChildren().setAll(anchorPane);
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText(null);
            alert.setContentText("Błędne dane logowania");
            alert.initOwner(root.getScene().getWindow());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> login.requestFocus());
    }
}
