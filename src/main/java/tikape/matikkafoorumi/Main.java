package tikape.matikkafoorumi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.matikkafoorumi.database.KeskusteluDao;
import tikape.matikkafoorumi.database.KategoriaDao;
import tikape.matikkafoorumi.domain.Keskustelunakyma;
import tikape.matikkafoorumi.domain.Viesti;
import tikape.matikkafoorumi.database.Database;
import tikape.matikkafoorumi.database.ViestiDao;
import tikape.matikkafoorumi.domain.Maara;

public class Main {

    public static void main(String[] args) throws Exception {
        Spark.staticFileLocation("/public");
        Database database = new Database("jdbc:sqlite:foorumi.db");
        database.init();

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        KategoriaDao kategoriaDao = new KategoriaDao(database);
        KeskusteluDao keskusteluDao = new KeskusteluDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("nakyma", kategoriaDao.luoAlkunakyma());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/kategoria/:nimi", (req, res) -> {
            String[] parsittu = req.params("nimi").split("-");
            HashMap map = new HashMap<>();
            int kategoriaId = kategoriaDao.findIdByName(parsittu[0]);
            List<Keskustelunakyma> kaikkiNakymat = keskusteluDao.luoKeskustelunakyma(kategoriaId);
            if(Integer.parseInt(parsittu[1]) - 1 > kaikkiNakymat.size() / 10){
                kaikkiNakymat = null;
            }
            List<Keskustelunakyma> kymmenenNakymaa = kymmenenNakymaa(kaikkiNakymat,parsittu[1]);
            map.put("nakyma", kymmenenNakymaa);
            map.put("kategoria", kategoriaDao.findOne(kategoriaId));
            map.put("maara", keskustelujenMaara(kaikkiNakymat, Integer.parseInt(parsittu[1])));
            return new ModelAndView(map, "keskustelut");
        }, new ThymeleafTemplateEngine());

        get("kategoria/:kategoriaNimi/:keskusteluId", (req, res) -> {
            HashMap map = new HashMap<>();
            String koko[] = req.params("keskusteluId").split("-");
            List<Viesti> kaikkiViestit = viestiDao.findAllByKeskustelu(Integer.parseInt(koko[0]));
            List<Viesti> kymmenenViestia = kymmenenViestia(kaikkiViestit,koko[1]);
            if(Integer.parseInt(koko[1]) - 1 > kaikkiViestit.size() / 10){
                kaikkiViestit = null;
            }
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
            viestiDao.addViesti(teksti, lahettaja, keskusteluId);
            List<Viesti> kaikkiViestit = viestiDao.findAllByKeskustelu(Integer.parseInt(koko[0]));
            res.redirect("/kategoria/" + req.params("kategoriaNimi") + "/" + koko[0] + "-" + (((kaikkiViestit.size()-1)/10) + 1));
            return "";
        });

        post("/kategoria/:nimi", (req, res) -> {
            String[] parsittu = req.params("nimi").split("-");
            Integer kategoriaId = kategoriaDao.findIdByName(parsittu[0]);
            String otsikko = req.queryParams("otsikko");
            String viesti = req.queryParams("viesti");
            String lahettaja = req.queryParams("lahettaja");
            keskusteluDao.addKeskustelu(otsikko, kategoriaId);
            int id = keskusteluDao.palautaViimeisin();
            viestiDao.addViesti(viesti, lahettaja, id);
            res.redirect("/kategoria/" + parsittu[0] + "/" + id +"-1");
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

    public static List<Keskustelunakyma> kymmenenNakymaa(List<Keskustelunakyma> kaikkiNakymat, String numero){
        int monesko = Integer.parseInt(numero);
        List<Keskustelunakyma> kymmenenNakymaa = new ArrayList<>();
        for (int i = (monesko * 10) - 10; i < monesko * 10; i++) {
            if(i >= kaikkiNakymat.size()){
                break;
            } else {
                kymmenenNakymaa.add(kaikkiNakymat.get(i));
            }
        }
        return kymmenenNakymaa;
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

    public static List<Maara> keskustelujenMaara(List<Keskustelunakyma> kaikkiKeskustelut, int tamanhetkinen){
        List<Maara> lista = new ArrayList<>();
        for (int i = 0; i < kaikkiKeskustelut.size(); i = i + 10) {
            if((i/10 + 1) == tamanhetkinen){
                lista.add(new Maara(i/10 + 1,true));
            } else {
                lista.add(new Maara(i/10 + 1,false));
            }
        }
        return lista;
    }
}
