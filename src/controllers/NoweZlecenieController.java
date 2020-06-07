package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.App;
import main.DbHandler;
import main.Main;
import main.Zlecenie;
import views.Views;

import java.net.URL;
import java.util.*;

public class NoweZlecenieController implements Initializable {
    public ComboBox<String> klienciCombo;
    public ComboBox<String> pracownicyCombo;
    public TextField kosztPrzewidywany;
    public TextArea opisZlecenia;
    public Button btn;

    private DbHandler db;
    private HashMap<Integer, String> hmapKlienci;
    private HashMap<Integer, String> hmapPracownicy;
    private List<String> klienci = new ArrayList<>();
    private List<String> pracownicy = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = App.getInstance().getDbHandler();
        hmapPracownicy = db.getPracownicyIdMap();

        setKlienciCombo();

        for(int key: hmapPracownicy.keySet()){
            pracownicy.add(hmapPracownicy.get(key));
        }

        pracownicyCombo.setItems(FXCollections.observableArrayList(pracownicy));
    }

    public void nowyKlient() {
        Main.createPopup(Views.NOWY_KLIENT);//tutaj ustawiany jest klient
        setKlienciCombo();
    }

    public void dodajZlecenie() {
        int idKlienta = getKey(hmapKlienci, klienciCombo.getSelectionModel().getSelectedItem());
        int idPracownika = getKey(hmapPracownicy, pracownicyCombo.getSelectionModel().getSelectedItem());
        Zlecenie z = new Zlecenie(0, idKlienta, "", null, null, kosztPrzewidywany.getText(), null, opisZlecenia.getText(), false);
        if(db.dodajZlecenie(z, idPracownika)){
            Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
            alertInfo.setTitle("Sukces");
            alertInfo.setHeaderText(null);
            alertInfo.setContentText("Zlecenie dodane");
            alertInfo.showAndWait();
            App.getInstance().archiwum = 0;
            Main.setView(Views.ZLECENIA);
        }else {
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Wystąpił nieoczekiwany błąd");
            alertErr.showAndWait();
        }
    }

    private void setKlienciCombo(){
        klienci.clear();
        hmapKlienci = db.getKlienciIdMap();
        for(int key: hmapKlienci.keySet()){
            klienci.add(hmapKlienci.get(key));
        }
        klienciCombo.setItems(FXCollections.observableArrayList(klienci));
    }

    public void powrotDoMenu(){
        Main.setView(Views.MENU);
    }

    private <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    //nowe zlecenie klient brany z globala
    //wypelnienie comboboxa klientami (imie nazwisko)
    //wypelnienie comboboxa pracownikami (imie nazwisko)
}
