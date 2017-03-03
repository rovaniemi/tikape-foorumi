package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
Täällä luodaan yhteys meidän foorumin tietokantaan ja jos tietokantaa ei vielä 
ole luotu, niin tämä koodi myös luo sen. 
*/

public class Database {
    private String databaseAddress;
    
    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen sekä seed datan luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Kategoria (id integer PRIMARY KEY, nimi varchar(200) NOT NULL, kuvaus varchar(500) NOT NULL);");
        lista.add("CREATE TABLE Keskustelu (id integer PRIMARY KEY, otsikko varchar(200), aika Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, kategoria integer, FOREIGN KEY (kategoria) REFERENCES Kategoria(id));");
        lista.add("CREATE TABLE Viesti(id integer PRIMARY KEY, viesti varchar(5000) NOT NULL, aika Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, keskustelu integer, FOREIGN KEY (keskustelu) REFERENCES Keskustelu(id));");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Integraali','Funktion integraalifunktio on sellainen funktio, jonka derivaatta on annettu funktio.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Derivaatta','Derivaatta tarkoittaa matematiikassa reaaliarvoja saavan funktion herkkyyttä muutokselle yhden sen riippumattoman muuttujan suhteen.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Matriisi','Matriisi on matematiikassa suorakulmainen riveihin ja sarakkeisiin jaettu taulukko.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Vektori','Vektori on suure, jolla on suuruus ja suunta.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Induktiotodistus','Matemaattinen induktio on matemaattinen todistusmenetelmä, joka kuuluu matemaattisen algebran päähaaraan.');");
        return lista;
    }
}
