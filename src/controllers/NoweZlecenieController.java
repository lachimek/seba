package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.App;
import main.DbHandler;
import main.Klient;
import main.Main;
import views.Views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

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
    private Klient k;

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

    }

    private void setKlienciCombo(){
        hmapKlienci = db.getKlienciIdMap();
        for(int key: hmapKlienci.keySet()){
            klienci.add(hmapKlienci.get(key));
        }
        klienciCombo.setItems(FXCollections.observableArrayList(klienci));
    }
    //nowe zlecenie klient brany z globala
    //wypelnienie comboboxa klientami (imie nazwisko)
    //wypelnienie comboboxa pracownikami (imie nazwisko)
}
