package namedEntity.Tema;

public class Politica extends Tema{
    String tematica;
    String subclass;
    
    public Politica(String tematicas){
        this.tematica = tematicas;
        this.subclass = "Politica";
    }

    public String getTema() {
        return tematica;
    }
    
    public String getSubclass() {
        return subclass;
    }
}

