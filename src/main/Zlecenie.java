package main;

import javafx.beans.property.*;

import java.sql.Date;

public class Zlecenie {
    IntegerProperty id = new SimpleIntegerProperty();
    IntegerProperty klientId = new SimpleIntegerProperty();
    StringProperty klient = new SimpleStringProperty();
    ObjectProperty<Date> start = new SimpleObjectProperty<>();
    ObjectProperty<Date> end = new SimpleObjectProperty<>();
    StringProperty kosztPrzewidywany = new SimpleStringProperty();
    StringProperty kosztKoncowy = new SimpleStringProperty();
    StringProperty opis = new SimpleStringProperty();
    BooleanProperty status = new SimpleBooleanProperty();

    public Zlecenie(int id, int klientId, String klient, Date start, Date end, String kosztPrzewidywany, String kosztKoncowy, String opis, boolean status) {
        this.id.set(id);
        this.klientId.set(klientId);
        this.klient.set(klient);
        this.start.set(start);
        this.end.set(end);
        this.kosztPrzewidywany.set(kosztPrzewidywany);
        this.kosztKoncowy.set(kosztKoncowy);
        this.opis.set(opis);
        this.status.set(status);
    }

    public Zlecenie(){}

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty klientIdProperty() {
        return klientId;
    }

    public StringProperty klientProperty() {
        return klient;
    }

    public ObjectProperty<Date> startProperty() {
        return start;
    }

    public ObjectProperty<Date> endProperty() {
        return end;
    }

    public StringProperty kosztPrzewidywanyProperty() {
        return kosztPrzewidywany;
    }

    public StringProperty kosztKoncowyProperty() {
        return kosztKoncowy;
    }

    public StringProperty opisProperty() {
        return opis;
    }

    public BooleanProperty statusProperty() {
        return status;
    }
}
