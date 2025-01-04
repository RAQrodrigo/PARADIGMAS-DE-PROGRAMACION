package namedEntity.Category.subcategory;

import namedEntity.heuristic.NamedEntity;
import namedEntity.Tema.Tema;

public class Country extends NamedEntity{
    String relacion;
    public Country(String name , Tema tema, Integer numArt){
        super(name,tema,"Country", numArt);
        if (name == "USA"){
            this.relacion = tema.getTema();
        }
        if(name == "Russia"){
            this.relacion = tema.getTema();
        }
    }
}
