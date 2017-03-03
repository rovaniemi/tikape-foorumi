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
import tikape.runko.domain.Maara;

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
            int kategoriaId = kategoriaDao.findIdByName(req.params("name"));
            map.put("nakyma", keskusteluDao.luoKeskustelunakyma(kategoriaId));
            map.put("kategoria", kategoriaDao.findOne(kategoriaId));
            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        get("kategoria/:kategoriaNimi/:keskusteluId", (req, res) -> {
            HashMap map = new HashMap<>();
            String koko[] = req.params("keskusteluId").split("-");
            List<Viesti> kaikkiViestit = viestiDao.findAllByKeskustelu(Integer.parseInt(koko[0]));
            List<Viesti> kymmenenViestia = kymmenenViestia(kaikkiViestit,koko[1]);
            map.put("viestit", kymmenenViestia);
            map.put("keskustelu", keskusteluDao.findOne(Integer.parseInt(koko[0])));
            int kategoriaId = kategoriaDao.findIdByName(req.params("kategoriaNimi"));
            map.put("kategoria", kategoriaDao.findOne(kategoriaId));
            map.put("maara", viestienMaara(kaikkiViestit,Integer.parseInt(koko[1])));
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/kategoria/:kategoriaNimi/:keskusteluId", (req, res) -> {
            String[] koko = req.params("keskusteluId").split("-");
            Integer keskusteluId = Integer.parseInt(koko[0]);
            String teksti = req.queryParams("teksti");
            String lahettaja = req.queryParams("lahettaja");
            viestiDao.addViesti(teksti + " t. " + lahettaja, keskusteluId);
            res.redirect("redirect:home");
            return "";
        });

        post("/kategoria/:name", (req, res) -> {

            Integer kategoriaId = kategoriaDao.findIdByName(req.params("name"));
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

    public static List<Viesti> kymmenenViestia(List<Viesti> kaikkiViestit, String numero){
        int monesko = Integer.parseInt(numero);
        List<Viesti> kymmenenViestia = new ArrayList<>();
        for (int i = (monesko * 10) - 10; i < monesko * 10; i++) {
            if(i >= kaikkiViestit.size()){
                break;
            } else {
                kymmenenViestia.add(kaikkiViestit.get(i));
            }
        }
        return kymmenenViestia;
    }

    public static List<Maara> viestienMaara(List<Viesti> kaikkiviestit, int tamanhetkinen){
        List<Maara> lista = new ArrayList<>();
        for (int i = 0; i < kaikkiviestit.size(); i = i + 10) {
            if((i/10 + 1) == tamanhetkinen){
                lista.add(new Maara(i/10 + 1,true));
            } else {
                lista.add(new Maara(i/10 + 1,false));
            }
        }
        return lista;
    }
}
