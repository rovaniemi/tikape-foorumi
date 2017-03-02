package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KategoriaDao;
import tikape.runko.database.KeskusteluDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class Main {

    public static void main(String[] args) throws Exception {
        Spark.staticFileLocation("/public");
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KategoriaDao kategoriaDao = new KategoriaDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nakyma", kategoriaDao.luoAlkunakyma());


            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());


        get("/kategoria/:name", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelu> keskustelut = new ArrayList<>();
            int keskusteluId = keskusteluDao.findIdByName(req.params("name"));
            keskustelut = keskusteluDao.findAllByKategoria(keskusteluId);

            map.put("keskustelut", keskustelut);
            map.put("kategoria", kategoriaDao.findOne(keskusteluId));

            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());



        get("kategoria/:kategoriaNimi/:keskusteluId", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Viesti> viestit = new ArrayList<>();
            viestit = viestiDao.findAllByKeskustelu(Integer.parseInt(req.params("keskusteluId")));

            map.put("viestit", viestit);
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(req.params("keskusteluId"))));
            int kategoriaId = keskusteluDao.findIdByName(req.params("kategoriaNimi"));
            map.put("kategoria", kategoriaDao.findOne(kategoriaId));

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/kategoria/:kategoriaNimi/:keskusteluId", (req, res) -> {
            Integer keskusteluId = Integer.parseInt(req.params(":keskusteluId"));
            String teksti = req.queryParams("teksti");

            String lahettaja = req.queryParams("lahettaja");
            viestiDao.addViesti(teksti + " t. " + lahettaja, keskusteluId);
            res.redirect("redirect:home");
            return "";
        });

        post("/kategoria/:name", (req, res) -> {

            Integer kategoriaId = keskusteluDao.findIdByName(req.params("name"));
            System.out.println(kategoriaId);
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");
            String lahettaja = req.queryParams("lahettaja");
            keskusteluDao.addKeskustelu(otsikko, kategoriaId);
            viestiDao.addViesti(viesti + " t. " + lahettaja, keskusteluDao.palautaViimeisin());
            
            res.redirect("redirect:home");
            return "";
        });

    }

}
