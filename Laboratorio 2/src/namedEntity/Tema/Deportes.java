package namedEntity.Tema;

public class Deportes extends Tema{
    String deporte;
    String subclass;

    public Deportes(String deportes){
        this.deporte = deportes;
        this.subclass = "Deportes";
    }

    public String getTema() {
        return deporte;
    }

    public String getSubclass() {
        return subclass;
    }

}