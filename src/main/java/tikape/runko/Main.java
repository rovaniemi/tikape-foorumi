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
import tikape.runko.domain.Keskustelu;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KategoriaDao kategoriaDao = new KategoriaDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
//        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kategoriat", kategoriaDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kategoriat", (req, res) -> {
            HashMap map = new HashMap<>();

            return new ModelAndView(map, "kategoriat");
        }, new ThymeleafTemplateEngine());

        get("/kategoriat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            List<Keskustelu> keskustelut = new ArrayList<>();
            keskustelut = keskusteluDao.findAllByKategoria(Integer.parseInt(req.params("id")));
            
            map.put("keskustelut", keskustelut);
            
            return new ModelAndView(map, "kategoria");
        }, new ThymeleafTemplateEngine());

    }
}
