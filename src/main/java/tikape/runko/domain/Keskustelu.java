package tikape.runko.domain;

/*
Tämä on meidän foorumiin liittyvän Keskustelu-käsitteen olio. Oliomuuttujina on
Keskustelu-taulun sarakkeet. Kun SQL-kyselyillä haetaan Keskustelu-taulusta 
tietoa, niin sarakkeiden arvot tallennetaan Keskustelu-olion oliomuuttujien 
arvoiksi. Esim. siis rivin X arvo otsikko-sarakkeessa tallennetaan Keskustelu-
olion otsikko-muuttujan arvoksi. 
*/

public class Keskustelu {
    private Integer id;
    private String otsikko;
    private String aika;
    private int kategoria;

    public Keskustelu(Integer id, String otsikko, String aika, Integer kategoria) {
        this.id = id;
        this.otsikko = otsikko;
        this.aika = aika;
        this.kategoria = kategoria;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOtsikko() {
        return this.otsikko;
    }

    public void setOtsikko(String otsikko) {
       this.otsikko = otsikko;
    }

    public String getAika() {
        return this.aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public Integer getKategoria(){
        return this.kategoria;
    }

    public void setKategoria(int kategoria){
        this.kategoria = kategoria;
    }
}
