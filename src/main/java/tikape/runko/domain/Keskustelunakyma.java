package tikape.runko.domain;

public class Keskustelunakyma {
    private int keskusteluId;
    private String keskusteluOtsikko;
    private int viestienMaara;
    private String viimeisinViesti;
    private int kategoriaId;

    public Keskustelunakyma(int keskusteluId, String keskusteluOtsikko, int viestienMaara, String viimeisinViesti, int kategoriaId) {
        this.keskusteluId = keskusteluId;
        this.keskusteluOtsikko = keskusteluOtsikko;
        this.viestienMaara = viestienMaara;
        this.viimeisinViesti = viimeisinViesti;
        this.kategoriaId = kategoriaId;
    }

    public int getKeskusteluId() {
        return keskusteluId;
    }

    public void setKeskusteluId(int keskusteluId) {
        this.keskusteluId = keskusteluId;
    }

    public String getKeskusteluOtsikko() {
        return keskusteluOtsikko;
    }

    public void setKeskusteluOtsikko(String keskusteluOtsikko) {
        this.keskusteluOtsikko = keskusteluOtsikko;
    }

    public int getViestienMaara() {
        return viestienMaara;
    }

    public void setViestienMaara(int viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    public String getViimeisinViesti() {
        return viimeisinViesti;
    }

    public void setViimeisinViesti(String viimeisinViesti) {
        this.viimeisinViesti = viimeisinViesti;
    }

    public int getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(int kategoriaId) {
        this.kategoriaId = kategoriaId;
    }
    
    
    
    
    
}
