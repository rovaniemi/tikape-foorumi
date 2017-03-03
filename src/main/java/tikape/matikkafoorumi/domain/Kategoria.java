package tikape.matikkafoorumi.domain;

/*
Tämä on Kategoria-käsitteen olioversio. Ks. Viesti- ja Keskustelu-luokat. Toimii
samalla tavalla. Materiaalissa luvussa 8.3 on hyödyllistä settiä.
*/

public class Kategoria {
    private Integer id;
    private String nimi;
    private String kuvaus;

    public Kategoria(Integer id, String nimi, String kuvaus) {
        this.id = id;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return this.nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

}