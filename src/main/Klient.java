package main;

import java.util.List;

public class Klient {
    private int id;
    private String imie;
    private String nazwisko;
    private List<String> telefony;
    private List<String> emaile;
    private List<Adres> adresy;

    public Klient(int id, String imie, String nazwisko, List<String> telefony, List<String> emaile, List<Adres> adresy) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefony = telefony;
        this.emaile = emaile;
        this.adresy = adresy;
    }

    public Klient(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void debug(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append('\n');
        sb.append("Imie: ").append(imie).append(" Nazwisko: ").append(nazwisko);
        sb.append("\nTelefony: ");
        telefony.forEach(telefon -> sb.append(telefon).append(" "));
        sb.append("\nEmaile: ");
        emaile.forEach(email -> sb.append(email).append(" "));
        sb.append("\nAdresy zamieszkania");
        adresy.forEach(adres -> sb.append(adres.toString()).append('\n'));
        sb.append('\n');

        System.out.println(sb.toString());
    }
}
