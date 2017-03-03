package tikape.matikkafoorumi.domain;

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
