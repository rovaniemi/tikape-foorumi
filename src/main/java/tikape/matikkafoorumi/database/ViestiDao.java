package tikape.matikkafoorumi.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tikape.matikkafoorumi.domain.Viesti;

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

    public void addViesti(String teksti, String henkilo, Integer keskusteluId) throws SQLException {
        if(teksti.equals("") || teksti.contains(">") || teksti.contains("<") || teksti.trim().length() == 0){
            return;
        }

        if(henkilo.equals("") || henkilo.contains("<") || henkilo.contains(">")){
            return;
        }

        teksti += " t. " + henkilo;
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (viesti, keskustelu) VALUES (?, ?)");
        stmt.setObject(1, teksti);
        stmt.setObject(2, keskusteluId);
        stmt.execute();
        stmt.close();
        connection.close();
    }


}
