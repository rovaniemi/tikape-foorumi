package tikape.matikkafoorumi.domain;

public class Alkunakyma {

    private String kategoriaNimi;
    private String lukumaara;
    private String aika;
    private String kategoriaId;
    private String kategoriaKuvaus;

    public Alkunakyma(String kategoria, String lukumaara, String aika, String kategoriaId, String kategoriaKuvaus) {
        this.kategoriaNimi = kategoria;
        this.lukumaara = lukumaara;
        this.aika = aika;
        this.kategoriaId = kategoriaId;
        this.kategoriaKuvaus = kategoriaKuvaus;
    }

    public String getAika() {
        return aika;
    }

    public String getKategoriaNimi() {
        return kategoriaNimi;
    }

    public String getKategoriaId() {
        return kategoriaId;
    }

    public String getLukumaara() {
        return lukumaara;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }

    public void setKategoria(String kategoria) {
        this.kategoriaNimi = kategoria;
    }

    public void setLukumaara(String lukumaara) {
        this.lukumaara = lukumaara;
    }

    public String getKategoriaKuvaus() {
        return kategoriaKuvaus;
    }

    public void setKategoriaKuvaus(String kategoriaKuvaus) {
        this.kategoriaKuvaus = kategoriaKuvaus;
    }

}
