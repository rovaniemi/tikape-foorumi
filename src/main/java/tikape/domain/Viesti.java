package tikape.domain;

/*
Tämä on meidän foorumiin liittyvän Viesti-käsitteen olio. Eli kun SQL-kyselyillä
haetaan tietoa jostain tietystä viestistä, niin se voidaan tallentaa tän
luokan mukaiseksi Viesti-olioksi. Eli esim. haetaan kyselyllä Viesti-taulun 
rivi X, ja tallennetaan sen rivin id-sarakkeen arvo tän olion id:n arvoksi, 
viesti-sarakkeen teksti tän olion viestimerkkijonon arvoksi jne. 
*/

public class Viesti {
    private Integer id;
    private String viesti;
    private String aika;
    private Integer keskustelu;

    public Viesti(Integer id, String viesti, String aika, int keskustelu) {
        this.id = id;
        this.viesti = viesti;
        this.aika = aika;
        this.keskustelu = keskustelu;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getViesti() {
        return this.viesti;
    }

    public void setViesti(String viesti) {
        this.viesti = viesti;
    }

    public String getAika() {
        return this.aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public Integer getKeskustelu() {
        return this.keskustelu;
    }

    public void setKeskustelu(Integer keskustelu) {
        this.keskustelu = keskustelu;
    }
}
