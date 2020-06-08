package controllers.popups;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import main.Adres;
import main.App;
import main.Klient;
import main.Main;
import views.Views;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NowyKlientController {
    public TextField imie;
    public TextField nazwisko;
    public TextArea telefony;
    public TextArea emaile;
    public TextArea adresy;
    public Button btn;

    private List<String> telefonyList = new ArrayList<>();
    private List<String> emaileList = new ArrayList<>();
    private List<Adres> adresyList = new ArrayList<>();

    public void dodajNrTel() {
        String res;
        if((res = otworzAlert(0)) != null){
            telefonyList.add(res);
            fillTextArea(telefony, telefonyList);
        }
    }

    public void cofnijNrTel() {
        telefonyList.remove(telefonyList.size()-1);
        fillTextArea(telefony, telefonyList);
    }

    public void dodajEmail() {
        String res;
        if((res = otworzAlert(1)) != null){
            emaileList.add(res);
            System.out.println(res);
            fillTextArea(emaile, emaileList);
        }
    }

    public void cofnijEmail() {
        emaileList.remove(emaileList.size()-1);
        fillTextArea(emaile, emaileList);
    }

    public void dodajAdres() {
        Main.createPopup(Views.NOWY_ADRES);
        adresyList.add(App.getInstance().nowyAdres);
        fillTextArea(adresy, adresyList);
    }

    public void cofnijAdres() {
        adresyList.remove(adresyList.size()-1);
        fillTextArea(adresy, adresyList);
    }

    public void utworzKlienta() {
        Klient k = new Klient(0, imie.getText(), nazwisko.getText(), telefonyList, emaileList, adresyList);
        App.getInstance().getDbHandler().dodajKlienta(k);
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    private void fillTextArea(TextArea area, List<?> data){
        area.setText("");
        data.forEach(item -> area.appendText(item.toString() +'\n'));
    }

    private String otworzAlert(int optype){
        String[] titles = {"Dodaj nr telefonu", "Dodaj adres email"};
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(titles[optype]);
        dialog.setHeaderText(null);
        dialog.setContentText(titles[optype]+":");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && optype == 0){
            return result.get().replaceAll("\\D+","");
        }else if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
