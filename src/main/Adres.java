package main;

public class Adres {
    String miasto;
    String kodPocztowy;
    String ulica;
    String nrBudynku;
    String nrLokalu;

    public Adres(String miasto, String kodPocztowy, String ulica, String nrBudynku, String nrLokalu) {
        this.miasto = miasto;
        this.kodPocztowy = kodPocztowy;
        this.ulica = ulica;
        this.nrBudynku = nrBudynku;
        this.nrLokalu = nrLokalu;
    }

    @Override
    public String toString() {
        return miasto + " " + kodPocztowy + " " + ulica + " " + nrBudynku + "/" + nrLokalu + '\n';
    }
}
