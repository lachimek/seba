package controllers.popups;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import main.App;
import main.DbHandler;
import main.Pracownik;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PracownikController implements Initializable {
    public TextField imie;
    public TextField nazwisko;
    public TextField tel;
    public TextField email;
    public Button btn;

    private Pracownik p;
    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = App.getInstance().getDbHandler();
        p = App.getInstance().wybranyPracownik;

        imie.setText(p.getImie());
        nazwisko.setText(p.getNazwisko());
        tel.setText(p.getNrTel());
        email.setText(p.getEmail());
    }

    public void zmienLiH() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Zmiana hasła");
        dialog.setHeaderText(null);
        ButtonType loginButtonType = new ButtonType("Zmień", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField pass1 = new PasswordField();
        pass1.setPromptText("Hasło");
        PasswordField pass2 = new PasswordField();
        pass2.setPromptText("Powtórz hasło");

        grid.add(new Label("Hasło:"), 0, 0);
        grid.add(pass1, 1, 0);
        grid.add(new Label("Hasło:"), 0, 1);
        grid.add(pass2, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        pass1.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || !pass1.getText().equals(pass2.getText())));

        pass2.textProperty().addListener((observable, oldValue, newValue) -> loginButton.setDisable(newValue.trim().isEmpty() || !pass1.getText().equals(pass2.getText())));

        dialog.getDialogPane().setContent(grid);
        dialog.setResultConverter(dialogButton -> {
            if (pass1.getText().equals(pass2.getText()) && dialogButton == loginButtonType) {
                return pass1.getText();
            }
            return null;
        });

        Optional<String> res = dialog.showAndWait();
        if(res.isPresent() && db.zmienHaslo(p.getId(), res.get())){
            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("Sukces");
            alertInfo.setHeaderText(null);
            alertInfo.setContentText("Zmiana hasła pomyślna");
            alertInfo.showAndWait();
        } else {
            Alert alertErr1 = new Alert(Alert.AlertType.ERROR);
            alertErr1.setTitle("Błąd");
            alertErr1.setHeaderText(null);
            alertErr1.setContentText("Wystąpił nieoczekiwany błąd");
            alertErr1.showAndWait();
        }
    }

    public void zapiszZmiany() {
        if(imie.getText().isEmpty() || nazwisko.getText().isEmpty() ||
        tel.getText().isEmpty() || email.getText().isEmpty()){
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Prosze uzupełnić puste pola");
            alertErr.showAndWait();
        }else {
            Pracownik nowy = new Pracownik(p.getId(), imie.getText(), nazwisko.getText(), tel.getText().replaceAll("\\D+",""), email.getText());
            if(db.modyfikujPracownika(nowy)){
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Sukces");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Edycja pracownika pomyślna");
                alertInfo.showAndWait();
                Stage stage = (Stage) btn.getScene().getWindow();
                stage.close();
            } else {
                Alert alertErr1 = new Alert(Alert.AlertType.ERROR);
                alertErr1.setTitle("Błąd");
                alertErr1.setHeaderText(null);
                alertErr1.setContentText("Wystąpił nieoczekiwany błąd");
                alertErr1.showAndWait();
            }
        }
    }
}
