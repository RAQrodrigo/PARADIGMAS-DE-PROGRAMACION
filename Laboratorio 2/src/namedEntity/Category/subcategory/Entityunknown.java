package namedEntity.Category.subcategory;
import namedEntity.heuristic.NamedEntity;
import namedEntity.Tema.Tema;

public class Entityunknown extends NamedEntity{
    String unknown;

    public Entityunknown(String name , Tema tema) {
        super(name,tema,"unknown");
        this.unknown = tema.getTema();
    }

}
