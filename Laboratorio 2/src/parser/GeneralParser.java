package parser;
import feed.Feed;
/*super es una llamada al constructor de la clase padre de 
la clase actual. Se utiliza para inicializar los miembros 
heredados de la clase padre antes de inicializar los miembros 
de la clase actual. En el ejemplo proporcionado, super se 
utiliza para llamar al constructor de la clase padre de GeneralParser,
 lo que significa que los miembros de la clase padre se 
 inicializan antes que los miembros de GeneralParser. En este caso, 
 super se utiliza para llamar al constructor predeterminado de la
  clase padre sin argumentos. */

  

/*Esta clase modela los atributos y metodos comunes a 
todos los distintos tipos de parser existentes en la aplicacion*/
public abstract class GeneralParser {
    
    public abstract Feed parser(String url); //preguntar si se puede castear o ver algun tipo abstracto
   
}