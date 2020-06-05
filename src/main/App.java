package main;

public class App {
    private final static App instance = new App();
    public String loggedInUser;
    // 1 = archiwum 0 = w trakcie
    public byte archiwum;
    public int wybraneZlecenieId;
    public int wybraneZlecenieKlientId;
    public Adres nowyAdres;

    public static App getInstance(){
        return instance;
    }

    private DbHandler dbHandler = new DbHandler();
    public DbHandler getDbHandler(){ return dbHandler; }

    private Klient klient = new Klient();
    public Klient getKlient() {
        return klient;
    }
    public void setKlient(Klient k){ klient = k; }

}
