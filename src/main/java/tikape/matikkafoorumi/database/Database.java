package tikape.matikkafoorumi.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        lista.add("CREATE TABLE Kategoria (id integer PRIMARY KEY, nimi varchar(200) NOT NULL, kuvaus varchar(500) NOT NULL);");
        lista.add("CREATE TABLE Keskustelu (id integer PRIMARY KEY, otsikko varchar(200), aika Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, kategoria integer, FOREIGN KEY (kategoria) REFERENCES Kategoria(id));");
        lista.add("CREATE TABLE Viesti(id integer PRIMARY KEY, viesti varchar(5000) NOT NULL, aika Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, keskustelu integer, FOREIGN KEY (keskustelu) REFERENCES Keskustelu(id));");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Integraali','Funktion integraalifunktio on sellainen funktio, jonka derivaatta on annettu funktio.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Derivaatta','Derivaatta tarkoittaa matematiikassa reaaliarvoja saavan funktion herkkyyttä muutokselle yhden sen riippumattoman muuttujan suhteen.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Matriisi','Matriisi on matematiikassa suorakulmainen riveihin ja sarakkeisiin jaettu taulukko.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Vektori','Vektori on suure, jolla on suuruus ja suunta.');");
        lista.add("INSERT INTO Kategoria (nimi, kuvaus) VALUES ('Induktiotodistus','Matemaattinen induktio on matemaattinen todistusmenetelmä, joka kuuluu matemaattisen algebran päähaaraan.');");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Miksi en osaa?',1)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Kumpulan integraalimäki',1)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Integraalit on parasta',1)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Mikä on derivaatta?',2)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Kuinka paljon voi derivoida?',2)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Miten derivoida?',2)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Matriisit dynaamisessa ohjelmoinnissa',3)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Matrix = matriisi?',3)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Haxxxx',3)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Matriisilla on väliä',3)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Pääseekö aladdin takaisin basaariin',4)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Oliko julius sitsaamassa?',4)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Mihin suuntaan vektori traktori menee',4)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Onko kaikki vektoreita',4)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Mikä ei ole vektori',4)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Alkuaskel on väärä, mutta muuten pätee. Onko tämä ok?',5)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Alkuaskel',5)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Induktio-oletus',5)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Induktioväite',5)");
        lista.add("INSERT INTO Keskustelu (otsikko, kategoria) VALUES ('Väite on todistettu',5)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Mulla on ongelma, enkä tiedä miten se ratkaistaan t. Pertti', 1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Vähän vaikee kuule auttaa, josset avaa ongelmaa vähän tarkemmin t. Martti', 1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Samaistun ekaan postaajaan, integraalit on vaikeita. :( t. Ulla', 1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Siis voisko oikeesti siihen hommata jonkun hiekotuksen t. Huolestunut', 2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('No samaa oon miettinyt t. Irkku', 2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Kumpulan jäämäki on paras mäki! t. Mirkku', 2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Liukastuin tänään... t. Pasi', 2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Tykkään integroida!!! t. Fani', 3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jeejee! t. Fantsufani', 3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Ihanaa, et täällä voi hehkuttaa integrointii! t. Sirppa', 3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Superii hyperii t. Samppa', 3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Voisko joku vähän avata tota derivaatan määritelmää. Tuntuu hankalalta. t. Heikkihei', 4)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Voiko derivoida liikaa t. Derivoija', 5)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('En oikeen pääse alkun. Miten aloittaa derivointi? t. Essi', 6)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Miten matriiseja voi hyödyntää dynaamisessa ohjelmoinnissa? t. Reiska', 7)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Onks matriisilla jotain yhteyttä matrixiin? t. Tietämätön', 8)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Haxxxxxhahhhxhxhxhhxx t. Hax', 9)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Mikähän pointti tässä oli? t. Lopeta', 9)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Matriisit kunniaan!!! t. Tumppi', 10)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jos Aladdinin matto kulkee vektoreiden x ja y suuntaan, pääseekö Aladdin lopulta takaisin basaariin? t. Irene', 11)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Oliks julius sitseil t. Hulius', 12)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jos on vektroitraktori nii mitä tapahtuu? t. Jussi', 13)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Voiko kaiken oikeestaan ajatella vektoreina? t. Jonna', 14)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Onko jotain, mikä ei ole vektori? Tällaista mietin. t. Jonna', 15)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Päättely toimii muuten hyvin. Onko siis ok? t. Olli', 16)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Tämä on alkuaskel. t. Todistaja', 17)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Seuraavaksi kai tulee induktio-oletus? t. Apuri', 17)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Ja sitten todistetaan väite todeksi induktio-oletuksen avulla. Helppo homma! t. Jeesari', 17)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Tämä on induktio-oletus. t. Todistaja', 18)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Sitten väitellään induktiosta t. Todistaja', 19)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jee! t. Apuri', 19)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Kaikki on todistettu! Siisti juttu! t. Todistaja', 20)");

        return lista;
    }
}
