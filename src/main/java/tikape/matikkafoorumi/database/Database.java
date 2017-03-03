package tikape.matikkafoorumi.database;

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
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Väite on todistettu',1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Geometriassa ja fysiikassa vektoreita',1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Käytetään kuvaamaan suureita, joihin suuruuden',1)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Lisäksi liittyy määrätty suunta.',2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Käytettyjen vektorien alkiot ovat reaalilukuja.',2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Kolmiulotteisessa avaruusgeometriassa ',2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Useimmissa fysikaalisissa sovelluksissa käytetyt vektorit kolmen reaaliluvun järjestettyinä',2)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Geometrisesti vektoreita voidaan kuvata janoilla, joiden toiseen päähän on tapana.',3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Tällöin kuitenkin kaikki suunnatut janat, jotka ovat yhtä pitkiä',3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Samansuuntaisia, katsotaan ekvivalenteiksi, toisin sanoen ne esittävät samaa vektoria.',3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Lukujonona tällaisissa vektoreissa on vain kolme tai tasogeometriassa kaksi lukua',3)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Ensimmäinen vastaa tämän janan projektiota x-akselin suunnassa, toinen y-akseli',4)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Erikoistapauksena on nollavektori',5)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('jossa kaikki nämä luvut ovat nollia. Sen suunta on määräämätön (mielivaltainen) ja pituus 0. ',6)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Geometriassa vektorilla voidaan kuvata annetun pisteen sijaintia suhteessa toiseen pisteeseen. ',7)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Pisteen paikkavektori kuvaa sen sijainnin suhteessa koordinaatiston origoon, ja sen alkioina ovat pisteen koordinaattien arvot. ',8)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Fysiikassa esimerkiksi nopeus on vektorisuure, ja sen itseisarvo, vauhti, on skalaari.',9)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Fysiikan eri aloilla tärkeitä vektorisuureita ovat myös kiihtyvyys ja voima sekä sähkö- ja magneettikenttien voimakkuudet. ',10)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Matriisi on matematiikassa suorakulmainen riveihin ja sarakkeisiin jaettu taulukko.',11)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jonka alkiot ovat lukuja (usein reaali- tai kompleksilukuja) tai lausekkeita. ',12)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Matriiseja käytetään yleisesti kaksiulotteisen tiedon havainnollistamiseen sekä lineaaristen yhtälöryhmien käsittelyyn ja ratkaisemiseen.',13)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Jos rajoitutaan yhden muuttujan funktioihin',14)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Voidaan muutosnopeuden keskiarvoa kuvata funktion kuvaajan keskimääräiseksi jyrkkyydeksi. ',15)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Sitä havainnollistetaan esimerkiksi Suomen lukioiden matematiikan oppimäärissä kuvaajan sekantilla (keskimääräinen muutosnopeus)',16)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('jonka kulmakerroin on kyseisellä välillä funktion jyrkkyyksien likiarvo.',16)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Mitä pienempi on sekantin rajaama väli.',16)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Sitä paremmin sen jyrkkyys vastaa funktion kuvaajan jyrkkyyksiä kyseisellä välillä.',17)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Lopulta, kun väliä pienennetään raja-arvon avulla pisteeksi.',18)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Saadaan derivaatta (muutosnopeus yhdessä kohdassa).',19)");
        lista.add("INSERT INTO Viesti (viesti,keskustelu) VALUES ('Sitä havainnollistetaan yleensä tangentilla, jonka kulmakerroin on derivaatan arvon suuruinen.',20)");
        return lista;
    }
}
