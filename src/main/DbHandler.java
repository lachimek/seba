package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//klasa do wykonywania operacji na bazie danych
public class DbHandler {
    //adres do bazy danych/nazwa bazy
    private final String url = "jdbc:mysql://localhost/seba";
    //odwolanie do polaczenia z baza danych
    private Connection conn;
    //konstruktor powodujacy uzyskanie polaczenia z baza
    private static final String salt = "696zaq*";
    public DbHandler(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            //w przypadku wystapienia wyjatkow wyrzuca je w konsoli
            e.printStackTrace();
        }
    }

    //metoda logowania zwraca true/false
    public boolean loginUser(String login, String pass){
        String q = "SELECT * FROM pracownik WHERE BINARY login = ? AND haslo = SHA1(?) AND pracuje = 1";
        PreparedStatement statement;
        try{
            statement = conn.prepareStatement(q);
            statement.setString(1, login);
            statement.setString(2, salt+pass);
            ResultSet set = statement.executeQuery();
            //jesli set istnieje to zwraca true i loguje usera
            boolean found = set.first();
            App.getInstance().loggedInUser = set.getString("Imie");
            statement.close();
            return found;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //metoda od rejestracji zwraca true/false
    public boolean registerUser(String login, String pass, String imie, String nazwisko, String telefon, String email){
        String q = "INSERT INTO `pracownik`(`id`, `Imie`, `Nazwisko`, `nr_tel`, `email`, `login`, `haslo`, `serwis_id`, `pracuje`) VALUES (DEFAULT,?,?,?,?,?,SHA(?),1, 1)";
        PreparedStatement statement;
        try{
            statement = conn.prepareStatement(q);
            statement.setString(1, imie);
            statement.setString(2, nazwisko);
            statement.setString(3, telefon);
            statement.setString(4, email);
            statement.setString(5, login);
            statement.setString(6, salt+pass);
            statement.execute();
            //true = zarejestrowano pomyslnie
            statement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ObservableList<Zlecenie> resultsetToZlecenie(ResultSet rs){
        ObservableList<Zlecenie> zlecenia = FXCollections.observableArrayList();
        try {
            while (rs.next()){
                String klient = rs.getString("Imie")+" "+rs.getString("Nazwisko");
                zlecenia.add(new Zlecenie(
                        rs.getInt("id"),
                        rs.getInt("klient_id"),
                        klient,
                        rs.getDate("data_roz"),
                        rs.getDate("data_zak"),
                        rs.getString("Koszt_przewidywany"),
                        rs.getString("Koszt_koncowy"),
                        rs.getString("Opis"),
                        rs.getBoolean("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zlecenia;
    }

    public ObservableList<Zlecenie> getZlecenia(){
        String q = "SELECT zlecenie.id, klient_id, klient.Imie AS Imie, klient.Nazwisko AS Nazwisko, data_roz, data_zak, Koszt_przewidywany, Koszt_koncowy, Opis, Status FROM zlecenie JOIN klient ON zlecenie.klient_id = klient.id WHERE Status=?";
        PreparedStatement stmt;
        ResultSet rs;
        ObservableList<Zlecenie> zlecenia = FXCollections.observableArrayList();
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, (App.getInstance().archiwum+1));
            rs = stmt.executeQuery();
            zlecenia = resultsetToZlecenie(rs);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return zlecenia;
    }

    public Zlecenie getZlecenieById(int id){
        String q = "SELECT zlecenie.id, klient_id, klient.Imie AS Imie, klient.Nazwisko AS Nazwisko, data_roz, data_zak, Koszt_przewidywany, Koszt_koncowy, Opis, Status FROM zlecenie JOIN klient ON zlecenie.klient_id = klient.id WHERE Status=? AND zlecenie.id = ?";
        PreparedStatement stmt;
        ResultSet rs;
        Zlecenie z = null;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, (App.getInstance().archiwum+1));
            stmt.setInt(2, id);
            rs = stmt.executeQuery();
            rs.first();
            String klient = rs.getString("Imie")+" "+rs.getString("Nazwisko");
            z = new Zlecenie(
                    rs.getInt("id"),
                    rs.getInt("klient_id"),
                    klient,
                    rs.getDate("data_roz"),
                    rs.getDate("data_zak"),
                    rs.getString("Koszt_przewidywany"),
                    rs.getString("Koszt_koncowy"),
                    rs.getString("Opis"),
                    rs.getBoolean("Status")
            );
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return z;
    }

    public Klient getKlientById(int id){
        String qEmaile = "SELECT email FROM email WHERE uzytkownik_id = ?";
        String qTelefony = "SELECT nr_tel FROM numer_telefonu WHERE uzytkownik_id = ?";
        String qAdresy = "SELECT `Miasto`, `Kod_pocztowy`, `Ulica`, `Numer_budynku`, `Numer_lokalu` FROM `adres` WHERE `uzytkownik_id` = ?";
        String qKlient = "SELECT Imie, Nazwisko FROM Klient WHERE id = ?";

        List<String> emaile = new ArrayList<>();
        List<String> telefony = new ArrayList<>();
        List<Adres> adresy = new ArrayList<>();
        String imie = "";
        String nazwisko = "";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(qEmaile);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()){
                emaile.add(rs.getString("email"));
            }
            stmt.close();
            stmt = conn.prepareStatement(qTelefony);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()){
                telefony.add(rs.getString("nr_tel"));
            }
            stmt.close();
            stmt = conn.prepareStatement(qAdresy);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()){
                adresy.add(new Adres(
                        rs.getString("Miasto"),
                        rs.getString("Kod_pocztowy"),
                        rs.getString("Ulica"),
                        rs.getString("Numer_budynku"),
                        rs.getString("Numer_lokalu")
                ));
            }
            stmt.close();
            stmt = conn.prepareStatement(qKlient);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.first();
            imie = rs.getString("Imie");
            nazwisko = rs.getString("Nazwisko");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Klient(id, imie, nazwisko, telefony, emaile, adresy);
    }

    public void dodajKlienta(Klient k){
        int insertId = getOstatnieIdKlienta()+1;
        String qKlient = "INSERT INTO `klient`(`id`, `Imie`, `Nazwisko`) VALUES (?,?,?)";
        String qEmail = "INSERT INTO `email`(`email`, `uzytkownik_id`) VALUES (?,?)";
        String qAdres = "INSERT INTO `adres`(`Miasto`, `Kod_pocztowy`, `Ulica`, `Numer_budynku`, `Numer_lokalu`, `uzytkownik_id`) VALUES (?,?,?,?,?,?)";
        String qTel = "INSERT INTO `numer_telefonu`(`nr_tel`, `uzytkownik_id`) VALUES (?,?)";

        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(qKlient);
            stmt.setInt(1, insertId);
            stmt.setString(2, k.getImie());
            stmt.setString(3, k.getNazwisko());
            stmt.execute();
            stmt.close();

            for(String email: k.getEmaile()){
                stmt = conn.prepareStatement(qEmail);
                stmt.setString(1, email);
                stmt.setInt(2, insertId);
                stmt.execute();
                stmt.close();
            }

            for(Adres adres: k.getAdresy()){
                stmt = conn.prepareStatement(qAdres);
                stmt.setString(1, adres.miasto);
                stmt.setString(2, adres.kodPocztowy);
                stmt.setString(3, adres.ulica);
                stmt.setString(4, adres.nrBudynku);
                stmt.setString(5, adres.nrLokalu);
                stmt.setInt(6, insertId);
                stmt.execute();
                stmt.close();
            }

            for(String tel: k.getTelefony()){
                stmt = conn.prepareStatement(qTel);
                stmt.setString(1, tel);
                stmt.setInt(2, insertId);
                stmt.execute();
                stmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean dodajZlecenie(Zlecenie z, int pracownikId){
        String q = "INSERT INTO `zlecenie`(`klient_id`, `data_roz`, `data_zak`, `Koszt_przewidywany`, `Koszt_koncowy`, `Opis`, `Status`) VALUES (?,now(),NULL,?,NULL,?,1)";
        String qPara = "INSERT INTO `zlec_prac`(`Zlecenie_id`, `pracownik_id`) VALUES (?,?)";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, z.klientId.get());
            stmt.setString(2, z.kosztPrzewidywany.get());
            stmt.setString(3, z.opis.get());
            stmt.execute();
            stmt.close();

            stmt = conn.prepareStatement(qPara);
            stmt.setInt(1, getOstatnieIdZlecenie());
            stmt.setInt(2, pracownikId);
            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getOstatnieIdZlecenie(){
        String q = "SELECT id FROM `zlecenie` ORDER BY id DESC LIMIT 1";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(q);
            rs.first();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Pracownik getPracownikById(int id){
        String q = "SELECT * FROM pracownik WHERE id = ? AND pracuje = 1";
        PreparedStatement stmt;
        ResultSet rs;
        Pracownik p = null;
        try {
            stmt = conn.prepareStatement(q);stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.first();
            p = new Pracownik(rs.getInt("id"),
                    rs.getString("Imie"),
                    rs.getString("Nazwisko"),
                    rs.getString("nr_tel"),
                    rs.getString("email"));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public HashMap<Integer, String> getKlienciIdMap(){
        String q = "SELECT * FROM klient";
        Statement statement;
        ResultSet rs;
        HashMap<Integer, String> hashMap = new HashMap<>();
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(q);
            while (rs.next()){
                hashMap.put(rs.getInt("id"), (rs.getString("Imie")+" "+rs.getString("Nazwisko")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public HashMap<Integer, String> getPracownicyIdMap(){
        String q = "SELECT id, Imie, Nazwisko FROM pracownik WHERE pracuje = 1";
        Statement statement;
        ResultSet rs;
        HashMap<Integer, String> hashMap = new HashMap<>();
        try{
            statement = conn.createStatement();
            rs = statement.executeQuery(q);
            while (rs.next()){
                hashMap.put(rs.getInt("id"), (rs.getString("Imie")+" "+rs.getString("Nazwisko")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public int getPracownikIdByZlecenieId(int id){
        String q = "SELECT pracownik_id FROM zlec_prac WHERE Zlecenie_id = ?";
        System.out.println("Zlecenie id: "+ id);
        PreparedStatement stmt;
        ResultSet rs;
        int Pid = 0;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.first();
            Pid = rs.getInt("pracownik_id");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Pid: "+Pid);
        return Pid;
    }

    public boolean usunZlecenie(int id){
        String q = "DELETE FROM `zlecenie` WHERE `id` = ?";
        PreparedStatement stmt;
        ResultSet rs;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            stmt.close();
            return rs.first();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean zakonczZlecenie(int kosztKoncowy, int id){
        String q = "UPDATE `zlecenie` SET Status = 2, data_zak = now(), Koszt_koncowy = ? WHERE `id` = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, kosztKoncowy);
            stmt.setInt(2, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void modyfikujOpis(String opis, int id){
        String q = "UPDATE `zlecenie` SET `Opis` = ? WHERE `id` = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setString(1, opis);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getOstatnieIdKlienta() {
        String q = "SELECT id FROM `klient` ORDER BY id DESC LIMIT 1";
        Statement stmt;
        ResultSet rs;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(q);
            rs.first();
            return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean usunPracownika(int id){
        String q = "UPDATE pracownik SET pracuje = 0 WHERE id = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setInt(1, id);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean zmienHaslo(int id, String pass){
        String q = "UPDATE pracownik SET haslo = SHA1(?) WHERE id = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setString(1, salt+pass);
            stmt.setInt(2, id);
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modyfikujPracownika(Pracownik p){
        String q = "UPDATE `pracownik` SET `Imie`=?,`Nazwisko`=?,`nr_tel`=?,`email`=? WHERE id = ?";
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setString(1, p.getImie());
            stmt.setString(2, p.getNazwisko());
            stmt.setString(3, p.getNrTel());
            stmt.setString(4, p.getEmail());
            stmt.setInt(5, p.getId());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Serwis getSerwisInfo(){
        String qSerwis = "SELECT nazwa, adres FROM serwis WHERE id = 1";
        String qEmail = "SELECT email FROM email_serwis WHERE serwis_id = 1";
        String qTel = "SELECT numer_tel FROM numer_tel_serwis WHERE serwis_id = 1";
        Serwis s = new Serwis();
        Statement statement;
        ResultSet rs;
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(qSerwis);
            rs.first();
            s.setNazwa(rs.getString("nazwa"));
            s.setAdres(rs.getString("adres"));
            rs.close();
            rs = statement.executeQuery(qEmail);
            rs.first();
            s.setEmail(rs.getString("email"));
            rs.close();
            rs = statement.executeQuery(qTel);
            rs.first();
            s.setTelefon(rs.getString("numer_tel"));
            rs.close();
            statement.close();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
            return s;
        }
    }

    public void updateSerwis(String data, int optype){//0 = adres // 1 = tel // 2 = email
        String q = "";
        switch (optype){
            case 0:
                q = "UPDATE serwis SET adres = ? WHERE id = 1";
                break;
            case 1:
                q = "UPDATE numer_tel_serwis SET numer_tel = ? WHERE serwis_id = 1";
                break;
            case 2:
                q = "UPDATE email_serwis SET email = ? WHERE serwis_id = 1";
                break;
        }
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(q);
            stmt.setString(1, data);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
