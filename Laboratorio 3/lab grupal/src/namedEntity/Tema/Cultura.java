package namedEntity.Tema;

public class Cultura extends Tema {
    String tematica;
    String subclass;

    public Cultura(String tematicas){
        this.tematica = tematicas;
        this.subclass = "Cultura" ;
    }

    public String getTema() {
        return tematica;
    }
    public String getSubclass() {
        return subclass;
    }
    
    
}
