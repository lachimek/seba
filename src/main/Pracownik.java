package main;

public class Pracownik {
    int id;
    String imie;
    String nazwisko;
    String nrTel;
    String email;

    public Pracownik(int id, String imie, String nazwisko, String nrTel, String email) {
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrTel = nrTel;
        this.email = email;
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

    public String getNrTel() {
        return nrTel;
    }

    public String getEmail() {
        return email;
    }
}
