/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author topi
 */
public class Alkunakyma {
    private String kategoriaNimi;
    private String lukumaara;
    private String aika;
    private String kategoriaId;
    

    public Alkunakyma(String kategoria, String lukumaara, String aika, String kategoriaId) {
        this.kategoriaNimi = kategoria;
        this.lukumaara = lukumaara;
        this.aika = aika;
        this.kategoriaId = kategoriaId;
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
    
    
    
}
