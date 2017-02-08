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

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        KategoriaDao kategoriaDao = new KategoriaDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kategoriat", kategoriaDao.findAll());

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

    }
}
