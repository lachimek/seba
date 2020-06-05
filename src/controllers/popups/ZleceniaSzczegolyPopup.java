package controllers.popups;

import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ZleceniaSzczegolyPopup implements Initializable {

    public TextField pracownik;
    public TextField klient;
    public ComboBox<String> telCombo;
    public ComboBox<String> emailCombo;
    public TextArea adresKlienta;
    public TextField KosztPrzew;
    public TextField kosztKonc;
    public TextArea opis;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DbHandler db = App.getInstance().getDbHandler();
        Klient k = db.getKlientById(App.getInstance().wybraneZlecenieKlientId);
        Pracownik p = db.getPracownikById(db.getPracownikIdByZlecenieId(App.getInstance().wybraneZlecenieId));
        Zlecenie z = db.getZlecenieById(App.getInstance().wybraneZlecenieId);

        pracownik.setText(p.getImie()+" "+ p.getNazwisko());
        klient.setText(k.getImie()+" "+ k.getNazwisko());
        telCombo.getItems().setAll(k.getTelefony());
        emailCombo.getItems().setAll(k.getEmaile());
        k.getAdresy().forEach(adres -> adresKlienta.appendText(adres.toString()+'\n'));
        KosztPrzew.setText(z.kosztPrzewidywanyProperty().get());
        kosztKonc.setText(z.kosztKoncowyProperty().get());
        opis.setText(z.opisProperty().get());
        telCombo.getSelectionModel().selectFirst();
        emailCombo.getSelectionModel().selectFirst();
    }
}
