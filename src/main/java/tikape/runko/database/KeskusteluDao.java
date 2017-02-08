package tikape.runko.database;

import tikape.runko.domain.Keskustelu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
Keskusteluihin liittyvä hakemis- ja tallennustoiminnallisuus löytyy täältä. 
Toteuttaa Dao-rajapinnan, eli löytyy metodit findOne, findAll ja delete.
Tämä luokka siis hoitaa käytännössä Keskustelu-tauluun liittyviä kyselyitä.
Kyselyiden perusteella luodaan Keskustelu-olioita, joita sitten palautetaan
yksittäin tai listana takaisin metodin kutsujalle.
*/

public class KeskusteluDao implements Dao<Keskustelu, Integer> {
    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    @Override
    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        String aika = rs.getString("aika");
        Integer kategoria = rs.getInt("kategoria");

        Keskustelu k = new Keskustelu(id, otsikko, aika, kategoria);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String aika = rs.getString("aika");
            Integer kategoria = rs.getInt("kategoria");
            keskustelut.add(new Keskustelu(id, otsikko, aika, kategoria));
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    //Tää on ite tehty uus metodi. Hakee Keskustelu-taulusta ne viestit, joilla on 
    //parametrina annettua key-muuttujaa vastaava arvo kategoria-sarakkeessa.
    //Elikkä siis hakee tiettyyn kategoriaan liittyvät keskustelut, ja palauttaa
    //ne listana.
    public List<Keskustelu> findAllByKategoria(int key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE Keskustelu.kategoria = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            String aika = rs.getString("aika");
            Integer kategoria = rs.getInt("kategoria");
            keskustelut.add(new Keskustelu(id, otsikko, aika, kategoria));
        }

        rs.close();
        stmt.close();
        connection.close();
        
        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}