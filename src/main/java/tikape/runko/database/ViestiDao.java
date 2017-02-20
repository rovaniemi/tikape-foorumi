package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

/*
Viesteihin liittyvä hakemis- ja tallennustoiminnallisuus löytyy täältä. 
Toteuttaa Dao-rajapinnan, eli löytyy metodit findOne, findAll ja delete.
Tämä luokka siis hoitaa käytännössä Viesti-tauluun liittyviä kyselyitä.
Kyselyiden perusteella luodaan Viesti-olioita, joita sitten palautetaan
yksittäin tai listana takaisin metodin kutsujalle.
 */
public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String viesti = rs.getString("viesti");
        String aika = rs.getString("aika");
        Integer keskustelu = rs.getInt("keskustelu");

        Viesti v = new Viesti(id, viesti, aika, keskustelu);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String viesti = rs.getString("viesti");
            String aika = rs.getString("aika");
            Integer keskustelu = rs.getInt("keskustelu");

            viestit.add(new Viesti(id, viesti, aika, keskustelu));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }
<<<<<<< HEAD
    
    /*Tää on ite tehty uus metodi. Hakee Viesti taulusta ne viestit, joilla on
    parametrina annettua key-muuttujaa vastaava arvo keskustelu-sarakkeessa.
    Elikkä siis hakee tietyn keskustelun viestit.*/

=======

    //Tää on ite tehty uus metodi. Hakee Viesti taulusta ne viestit, joilla on 
    //parametrina annettua key-muuttujaa vastaava arvo keskustelu-sarakkeessa.
    //Elikkä siis hakee tietyn keskustelun viestit.
>>>>>>> c87726e412a68f842280a816cbb018168e3a1eaf
    public List<Viesti> findAllByKeskustelu(int key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE Viesti.keskustelu = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {
            Integer id = rs.getInt("id");
            String viesti = rs.getString("viesti");
            String aika = rs.getString("aika");
            Integer keskustelu = rs.getInt("keskustelu");

            viestit.add(new Viesti(id, viesti, aika, keskustelu));
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public void addViesti(String teksti, Integer keskusteluId) throws SQLException {
        System.out.println("sdoadoakdoskd");
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (viesti, keskustelu) VALUES ('teksti', keskusteluId)");
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        ResultSet rs = stmt.executeQuery();
        System.out.println("beeeeeeeeeeeeeeeeee");

        rs.close();
        stmt.close();
        connection.close();
    }

    @Override
    public void delete(Integer key) throws SQLException {
        //ei toteutettu
    }

}
