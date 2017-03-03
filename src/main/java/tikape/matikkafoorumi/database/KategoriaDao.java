package tikape.matikkafoorumi.database;

import tikape.matikkafoorumi.domain.Kategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import tikape.matikkafoorumi.domain.Alkunakyma;

public class KategoriaDao implements Dao<Kategoria, Integer> {

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

    public int findIdByName(String nimi) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT id FROM Kategoria WHERE nimi = ?");
        stmt.setObject(1, nimi);

        ResultSet rs = stmt.executeQuery();
        List<Integer> kokonaisluku = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            kokonaisluku.add(id);
        }
        rs.close();
        stmt.close();
        connection.close();
        return kokonaisluku.get(0);
    }

    public List<Alkunakyma> luoAlkunakyma() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Kategoria.id, Kategoria.nimi AS Kategoria, "
                + "Kategoria.kuvaus AS Kuvaus, COUNT(Viesti.viesti) AS Viesteja_yhteensa, "
                + "MAX(Viesti.aika) AS Viimeisin_viesti\n"
                + "FROM Kategoria, Viesti, Keskustelu\n"
                + "WHERE Keskustelu.id = Viesti.keskustelu \n"
                + "AND Kategoria.id = Keskustelu.kategoria\n"
                + "GROUP BY Kategoria.id\n"
                + "ORDER BY Kategoria ASC");

        ResultSet rs = stmt.executeQuery();

        List<Alkunakyma> nakyma = new ArrayList<>();

        while (rs.next()) {
            String kategoria = rs.getString("Kategoria");
            String lukumaara = rs.getString("Viesteja_yhteensa");
            String aika = rs.getString("Viimeisin_viesti");
            String kuvaus = rs.getString("Kuvaus");
            DateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat writeFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
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

            String id = rs.getString("Id");

            nakyma.add(new Alkunakyma(kategoria, lukumaara, aika, id, kuvaus));

        }

        rs.close();
        stmt.close();
        connection.close();
        return nakyma;
    }

}
