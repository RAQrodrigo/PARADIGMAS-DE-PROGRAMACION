package namedEntity.Category.subcategory;


import namedEntity.heuristic.NamedEntity;
import namedEntity.Tema.Tema;


public class Persona extends NamedEntity {
    String profesion;
   
    public Persona(String name , Tema tema, Integer numArt) {
        super(name,tema, "Person", numArt);
        if (name == "Messi"){
            this.profesion = tema.getTema();
        }
        if(name == "Biden"){
            this.profesion = tema.getTema();
        }
        if(name == "Musk") {
            this.profesion = tema.getTema();
        }
        if(name == "Federer") {
            this.profesion = tema.getTema();
        }
        if(name == "Trump"){
            this.profesion = tema.getTema();
        }
    }
}
