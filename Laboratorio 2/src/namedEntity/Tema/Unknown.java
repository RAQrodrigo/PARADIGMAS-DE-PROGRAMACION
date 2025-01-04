package namedEntity.Tema;

public class Unknown extends Tema {
    String tematica;
    String subclass;
    
    public Unknown(String tematicas){
        this.tematica = tematicas;
        this.subclass = "Sin subclase";
    }

    public String getTema() {
        return tematica;
    }
    
    public String getSubclass() {
        return subclass;
    }


}
