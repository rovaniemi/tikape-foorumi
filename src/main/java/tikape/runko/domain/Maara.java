package tikape.runko.domain;

public class Maara {
    private int id;
    private boolean onkoSivu;

    public Maara(int id, boolean onko){
        this.id = id;
        this.onkoSivu = onko;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOnkoSivu(boolean onkoSivu) {
        this.onkoSivu = onkoSivu;
    }

    public int getId() {
        return id;
    }

    public boolean getOnkoSivu() {
        return onkoSivu;
    }
}
