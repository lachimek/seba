package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.App;
import main.DbHandler;
import main.Main;
import main.Zlecenie;
import views.Views;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ArchiwumController implements Initializable {

    public AnchorPane root;
    public TableView<Zlecenie> table;
    public TableColumn<Zlecenie, Integer> id;
    public TableColumn<Zlecenie, String> klient;
    public TableColumn<Zlecenie, Date> start;
    public TableColumn<Zlecenie, Date> end;
    public TableColumn<Zlecenie, String> opis;
    public Label label;

    private DbHandler db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(App.getInstance().archiwum == 1){
            label.setText("Archiwum");
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

    public void powrotDoMenu() {
        Main.setView(Views.MENU);
    }
}
