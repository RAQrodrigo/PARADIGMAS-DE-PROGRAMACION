UPDATE

Nota  8

# Comentarios

Mucho mejor, aunque todavía quedan un par de cosas:

- En el main se sigue repitendo mucho código para mostrar los feeds y para `-ne`.
- Sigue estando rara la clasificación de entidades. Por ejemplo, en `Company.java`:

```java
    public Company(String name , Tema tema) {
            super(name, tema, "Company");
            
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
```

- Solo para que quede registro, aunque no afectó la nota, acostúmbrense a usar un linter para que el código quede mejor estructurado.

--- Anterior

Nota 3

# Comentarios

Mucho código repetido, nulo uso de las interfaces comunes, e implementación incompleta.

- Repiten un montón de código en el main. No hacen uso de que las heurísticas tienen la misma interfaz. También en el feed reader, pero para una parte que no implementan (el parser json)
- Para la iteración, mejor usar la construcción `for (Type x : collection)` (detalle)
- En vez de muchos ifs, debería usar un switch que es para eso.
- Hacen muchas veces la misma línea en las entidades `this.that = tema`.
- No se imprimen los resultados de las heurísticas?
