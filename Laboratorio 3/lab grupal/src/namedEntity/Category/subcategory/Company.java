package namedEntity.Category.subcategory;
import namedEntity.heuristic.NamedEntity;
import namedEntity.Tema.Tema;
//import namedEntity.Tema.Cultura;

public class Company extends NamedEntity{
    String orientacion;

    public Company(String name , Tema tema, Integer numArt) {
            super(name, tema, "Company", numArt);
            
            if (name == "Microsoft"){
                this.orientacion = tema.getTema();
            } 
            if(name == "Apple"){
                this.orientacion = tema.getTema();
            }
            if(name == "Google") {
                this.orientacion = tema.getTema();   
            }
            if(name == "Uber") {
                this.orientacion = tema.getTema();
            }
    }
}

