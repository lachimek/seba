package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.App;
import main.DbHandler;
import main.Main;
import main.Serwis;
import views.Views;

import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ZarzadzanieController implements Initializable {


    public TextField adres;
    public TextField tel;
    public TextField email;
    public ComboBox<String> pracownicyCombo;
    public Label serwisLbl;

    private DbHandler db;
    private HashMap<Integer, String> hmapPracownicy;
    private List<String> pracownicy = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = App.getInstance().getDbHandler();
        populateTextFields();
        populatePracownicy();
        pracownicyCombo.getSelectionModel().selectFirst();

    }

    public void usunPracownika() {
        int idPracownika = getKey(hmapPracownicy, pracownicyCombo.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź usuwanie");
        alert.setHeaderText(null);
        alert.setContentText("Czy chcesz usunąć tego pracownika?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (db.usunPracownika(idPracownika)) {
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Sukces");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Usuwanie przebiegło pomyślnie");
                alertInfo.showAndWait();
                populatePracownicy();
            } else {
                Alert alertErr1 = new Alert(Alert.AlertType.ERROR);
                alertErr1.setTitle("Błąd");
                alertErr1.setHeaderText(null);
                alertErr1.setContentText("Wystąpił nieoczekiwany błąd");
                alertErr1.showAndWait();
            }
        }
    }

    public void edytujPracownika() {
        App.getInstance().wybranyPracownik = db.getPracownikById(getKey(hmapPracownicy, pracownicyCombo.getSelectionModel().getSelectedItem()));
        Main.createPopup(Views.PRACOWNIK);
        populatePracownicy();
    }

    public void zmienAdres(){
        String res = otworzAlert(0, adres.getText());
        if(res != null) {
            db.updateSerwis(res, 0);
            populateTextFields();
        }
    }

    public void zmienTelefon(){
        String res = otworzAlert(1, tel.getText());
        if(res != null) {
            db.updateSerwis(res, 1);
            populateTextFields();
        }
    }

    public void zmienEmail(){
        String res = otworzAlert(2, email.getText());
        if(res != null){
            db.updateSerwis(res, 2);
            populateTextFields();
        }
    }

    public void powrotDoMenu() {
        Main.setView(Views.MENU);
    }

    private void populatePracownicy(){
        pracownicy.clear();
        hmapPracownicy = db.getPracownicyIdMap();
        for(int key: hmapPracownicy.keySet()){
            pracownicy.add(hmapPracownicy.get(key));
        }
        pracownicyCombo.setItems(FXCollections.observableArrayList(pracownicy));
    }

    private void populateTextFields(){
        Serwis s = db.getSerwisInfo();
        serwisLbl.setText(s.getNazwa());
        adres.setText(s.getAdres());
        tel.setText(s.getTelefon());
        email.setText(s.getEmail());
    }

    private int getKey(Map<Integer, String> map, String data){
        AtomicInteger klucz = new AtomicInteger();
        map.forEach((key, value) -> {
            if (value.equals(data)) {
                klucz.set(key);
            }
        });
        return klucz.get();
    }

    private String otworzAlert(int optype, String base){
        String[] titles = {"Edycja adresu", "Edycja telefonu", "Edycja email"};
        TextInputDialog dialog = new TextInputDialog(base);
        dialog.setTitle(titles[optype]);
        dialog.setHeaderText(null);
        dialog.setContentText(titles[optype]+":");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && optype == 1){
            return result.get().replaceAll("\\D+","");
        }else if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
