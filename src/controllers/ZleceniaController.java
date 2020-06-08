package controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import main.App;
import main.DbHandler;
import main.Main;
import main.Zlecenie;
import views.Views;

import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class ZleceniaController implements Initializable {

    public AnchorPane root;
    public TableView<Zlecenie> table;
    public TableColumn<Zlecenie, Integer> id;
    public TableColumn<Zlecenie, String> klient;
    public TableColumn<Zlecenie, Date> start;
    public TableColumn<Zlecenie, Date> end;
    public TableColumn<Zlecenie, String> opis;
    public Label label;
    public Button deleteBtn;
    public Button updateBtn;
    public Button endBtn;

    private DbHandler db;
    private int zlecenieId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(App.getInstance().archiwum == 1){
            label.setText("Archiwum");
            deleteBtn.setVisible(false);
            updateBtn.setVisible(false);
            endBtn.setVisible(false);
        }else {
            label.setText("Zlecenia");
        }
        db = App.getInstance().getDbHandler();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        klient.setCellValueFactory(new PropertyValueFactory<>("klient"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));
        opis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        popualteTable();
    }

    private void popualteTable(){
        table.setItems(db.getZlecenia());
    }

    public void pokazSzczegoly() {
        App.getInstance().wybraneZlecenieKlientId = table.getSelectionModel().getSelectedItem().klientIdProperty().get();
        App.getInstance().wybraneZlecenieId = table.getSelectionModel().getSelectedItem().idProperty().get();
        Main.createPopup(Views.ZLECENIA_SZCZEGOLY);
    }

    public void usunZlecenie() {
        if(sprawdzCzyWybrano(1)){
            if(db.usunZlecenie(zlecenieId)){
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Sukces");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Usuwanie przebiegło pomyślnie");
                alertInfo.showAndWait();
                popualteTable();
            }else {
                Alert alertErr = new Alert(Alert.AlertType.ERROR);
                alertErr.setTitle("Błąd");
                alertErr.setHeaderText(null);
                alertErr.setContentText("Wystąpił nieoczekiwany błąd");
                alertErr.showAndWait();
            }
        }
    }

    public void modyfikujOpis() {
        if(sprawdzCzyWybrano(2)) {
            TextArea textArea = new TextArea(table.getSelectionModel().getSelectedItem().opisProperty().get());
            textArea.setWrapText(true);
            GridPane gridPane = new GridPane();
            gridPane.setMaxWidth(Double.MAX_VALUE);
            gridPane.add(textArea, 0, 0);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Zmiana opisu");
            alert.getDialogPane().setContent(gridPane);
            alert.showAndWait();

            db.modyfikujOpis(textArea.getText(), zlecenieId);
            popualteTable();
        }
    }

    public void zakonczZlecenie() {
        if(sprawdzCzyWybrano(1) && table.getSelectionModel().getSelectedItem().kosztKoncowyProperty() != null){
            TextInputDialog dialog = new TextInputDialog("0");
            dialog.setTitle("Zakończ zlecenie");
            dialog.setHeaderText("Ustaw cene końcową");
            dialog.setContentText("Cena końcowa:");
            int kosztKoncowy = 0;
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                String res = result.get().replaceAll("\\D+","");
                kosztKoncowy = Integer.parseInt(res);
            }
            if(kosztKoncowy != 0 && db.zakonczZlecenie(kosztKoncowy, zlecenieId)){
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setTitle("Sukces");
                alertInfo.setHeaderText(null);
                alertInfo.setContentText("Zlecenie zakończone i przeniesione do archiwum");
                alertInfo.showAndWait();
                popualteTable();
            }else {
                Alert alertErr = new Alert(Alert.AlertType.ERROR);
                alertErr.setTitle("Błąd");
                alertErr.setHeaderText(null);
                alertErr.setContentText("Wystąpił nieoczekiwany błąd");
                alertErr.showAndWait();
            }
        }
    }

    private boolean sprawdzCzyWybrano(int optype){
        String[] contentOptions = {"Czy chcesz usunąć to zlecenie?", "Czy chcesz zakonczyć to zlecenie?", "Czy chcesz zmienić opis?"};
        try {
            zlecenieId = table.getSelectionModel().getSelectedItem().idProperty().get();
            Alert alertConf = new Alert(Alert.AlertType.CONFIRMATION);
            alertConf.setTitle("Potwierdź usuwanie");
            alertConf.setHeaderText(null);
            alertConf.setContentText(contentOptions[optype]);
            Optional<ButtonType> result = alertConf.showAndWait();
            return(result.isPresent() && result.get() == ButtonType.OK); //klikniecie tak = true
        }catch (NullPointerException e){ //zlecenieId wyrzuci null pointera bo selected item bedzie null
            Alert alertErr = new Alert(Alert.AlertType.ERROR);
            alertErr.setTitle("Błąd");
            alertErr.setHeaderText(null);
            alertErr.setContentText("Proszę wybrać zlecenie z listy");
            alertErr.showAndWait();
            return false;
        }
    }

    public void powrotDoMenu() {
        Main.setView(Views.MENU);
    }
}
