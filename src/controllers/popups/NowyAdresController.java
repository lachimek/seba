package controllers.popups;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Adres;
import main.App;

public class NowyAdresController {
    public TextField miasto;
    public TextField kod1;
    public TextField kod2;
    public TextField ulica;
    public TextField nrBud;
    public TextField nrLok;
    public Button btn;

    public void dodajAdres() {
        if(miasto.getText().isEmpty() || kod1.getText().isEmpty() ||
        kod2.getText().isEmpty() || ulica.getText().isEmpty() ||
        nrBud.getText().isEmpty() || nrLok.getText().isEmpty()){
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Prosze uzupełnić puste pola");
            alertErr.showAndWait();
        }else{
            String kodFormatted = kod1.getText() +"-"+kod2.getText();
            App.getInstance().nowyAdres = new Adres(miasto.getText(), kodFormatted, ulica.getText(), nrBud.getText(), nrLok.getText());
            Stage stage = (Stage) btn.getScene().getWindow();
            stage.close();
        }
    }
}
