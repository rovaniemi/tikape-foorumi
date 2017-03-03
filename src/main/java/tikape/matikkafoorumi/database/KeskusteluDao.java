package tikape.matikkafoorumi.database;

import tikape.matikkafoorumi.domain.Keskustelu;
import tikape.matikkafoorumi.domain.Keskustelunakyma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void addKeskustelu(String teksti, Integer kategoriaId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Keskustelu (otsikko, kategoria) VALUES (?, ?)");
        stmt.setObject(1, teksti);
        stmt.setObject(2, kategoriaId);
        stmt.execute();
        stmt.close();
        connection.close();
    }

    public Integer palautaViimeisin() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(id) FROM Keskustelu");
        ResultSet rs = stmt.executeQuery();
        int viimeisin = rs.getInt("MAX(id)");
        rs.close();
        stmt.close();
        connection.close();
        return viimeisin;
    }

    public List<Keskustelunakyma> luoKeskustelunakyma(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Keskustelu.id AS keskusteluId, Keskustelu.otsikko AS otsikko, Kategoria.id AS kategoriaId, "
                + "COUNT(Viesti.viesti) AS viestit_yhteensa, "
                + "MAX(Viesti.aika) AS viimeisin_viesti "
                + "FROM Keskustelu, Viesti, Kategoria "
                + "WHERE Keskustelu.id = Viesti.keskustelu "
                + "AND Keskustelu.kategoria = Kategoria.id "
                + "AND Kategoria.id = ? "
                + "GROUP BY Keskustelu.otsikko "
                + "ORDER BY viimeisin_viesti DESC");
        stmt.setObject(1, key);
        ResultSet rs = stmt.executeQuery();
        
        List<Keskustelunakyma> nakyma = new ArrayList<>();
        
        while (rs.next()) {
            int id = rs.getInt("keskusteluId");
            String otsikko = rs.getString("otsikko");
            int viestit = rs.getInt("viestit_yhteensa");
            String aika = rs.getString("viimeisin_viesti");
            int kategoriaId = rs.getInt("kategoriaId");

            DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat writeFormat = new SimpleDateFormat("HH:mm dd.MM");
            Date date = null;
            try {
                date = readFormat.parse(aika);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = "";
            if (date != null) {
                formattedDate = writeFormat.format(date);
            }
            aika = formattedDate;

            nakyma.add(new Keskustelunakyma(id, otsikko, viestit, aika, kategoriaId));
        }

        rs.close();
        stmt.close();
        connection.close();
        return nakyma;
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }
}
