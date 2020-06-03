package main;

import java.util.List;

public class Klient {
    int id;
    String imie;
    String nazwisko;
    List<String> telefony;
    List<String> emaile;
    List<Adres> adresy;

    public Klient(int id, String imie, String nazwisko, List<String> telefony, List<String> emaile, List<Adres> adresy) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefony = telefony;
        this.emaile = emaile;
        this.adresy = adresy;
    }

    public int getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public List<String> getTelefony() {
        return telefony;
    }

    public List<String> getEmaile() {
        return emaile;
    }

    public List<Adres> getAdresy() {
        return adresy;
    }
}
