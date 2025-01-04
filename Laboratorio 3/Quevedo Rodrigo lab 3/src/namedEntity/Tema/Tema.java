package namedEntity.Tema;
import java.io.Serializable;

public abstract class Tema implements Serializable{
    public abstract String getTema();
    public abstract String getSubclass();

    public String tema() {
        return "Sin tema";
    }

    public String subclass() {
        return "Sin Subclass";
    }
}
