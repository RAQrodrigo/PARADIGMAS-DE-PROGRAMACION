package namedEntity.Tema;

public abstract class Tema {
    public abstract String getTema();
    public abstract String getSubclass();

    public String tema() {
        return "Sin tema";
    }

    public String subclass() {
        return "Sin Subclass";
    }
}
