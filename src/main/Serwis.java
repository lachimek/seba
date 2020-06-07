package main;

public class Serwis {
    String nazwa;
    String adres;
    String telefon;
    String email;

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getAdres() {
        return adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public String getEmail() {
        return email;
    }
}
