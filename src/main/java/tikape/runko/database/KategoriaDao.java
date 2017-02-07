package tikape.runko.database;

import tikape.runko.domain.Kategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategoriaDao implements Dao<Kategoria, Integer>{
    private Database database;
    
    public KategoriaDao(Database database) {
        this.database = database;
    }

    @Override
    public Kategoria findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kategoria WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");
        String kuvaus = rs.getString("kuvaus");

        Kategoria k = new Kategoria(id, nimi, kuvaus);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kategoria> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kategoria");

        ResultSet rs = stmt.executeQuery();
        List<Kategoria> kategoriat = new ArrayList<>();
        
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");
            String kuvaus = rs.getString("kuvaus");
            kategoriat.add(new Kategoria(id, nimi, kuvaus));
        }

        rs.close();
        stmt.close();
        connection.close();

        return kategoriat;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}


