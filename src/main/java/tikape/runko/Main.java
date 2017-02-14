package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KategoriaDao;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class Main {

    /*KÄYNNISTYS EKAA KERTAA: Kun ajat tän projektin ekaa kertaa, niin Database-
    luokassa oleva koodi (database kansiossa) luo foorumi.db-nimisen tietokannan. 
    Siellä on taulujen luonnin lisäksi valmiiksi lisätty alkudataa foorumille, eli 
    muutamia aihealueita, aiheisiin liittyviä keskusteluja sekä keskusteluihin liittyviä 
    viestejä. Noille taululle voi tosiaan tehdä ihan normisti SQL-kyselyitä 
    sqliten kautta komentorivillä, ja kokeilla mitä eri kyselyt palauttaa.
    MUUTEN: Tää Main-luokka luo databasen lisäksi noi daot, joihin on eriytetty
    eri tauluihin liittyvät kyselyt. Niiden avulla sitten määritellään,
    mitä eri sivuilla näkyy. Html-tiedostot löytyy src/main/resources/templates.
     */
    public static void main(String[] args) throws Exception {
        //yhdistäminen tietokantaan
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        //eri kyselyjä käsittelevien osien luonti
        KategoriaDao kategoriaDao = new KategoriaDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        //täällä tehään niitä hakupyyntöjä eri osotteille
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nakyma", kategoriaDao.luoAlkunakyma());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelu> keskustelut = new ArrayList<>();
            keskustelut = keskusteluDao.findAllByKategoria(Integer.parseInt(req.params("id")));

            map.put("keskustelut", keskustelut);
            map.put("kategoria", kategoriaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        get("/:kategoriaId/:keskusteluId", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = new ArrayList<>();
            viestit = viestiDao.findAllByKeskustelu(Integer.parseInt(req.params("keskusteluId")));

            map.put("viestit", viestit);
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(req.params("keskusteluId"))));
            map.put("kategoria", kategoriaDao.findOne(Integer.parseInt(req.params("kategoriaId"))));

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        System.out.println(kategoriaDao.luoAlkunakyma());

    }

}
