package Modularizar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import feed.Article;
import namedEntity.heuristic.NamedEntity;
import namedEntity.heuristic.Heuristic;
import scala.Tuple2;
import scala.Tuple3;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;

public class FilePrinter {

    public static void Printear(JavaPairRDD<String, Integer> funcion, String nombre) {
        System.out.println("\n");
    funcion.foreach(tuple -> {
        System.out.println(nombre + tuple._1() + " " + tuple._2() + "\n");
    });
    }

    public static JavaPairRDD<String, Integer> generatePairRDD(JavaRDD<List<NamedEntity>> transformedRDD, String field) {
    return transformedRDD.flatMapToPair(entityList -> {
        List<Tuple2<String, Integer>> wordCountList = new ArrayList<>();
        for (NamedEntity entity : entityList) {
            String key = "";
            Integer value = 0;
            switch (field) {
                case "Name":
                    key = entity.getName();
                    value = entity.getFrequency();
                    break;
                case "Class":
                    key = entity.getClase();
                    value = 1;
                    break;
                case "Subclass":
                    key = entity.getSubclass();
                    value = 1;
                    break;
                case "ClassTema":
                    key = entity.getSubclassTema();
                    value = 1;
            }
            wordCountList.add(new Tuple2<>(key, value));
        }
        return wordCountList.iterator();
    }).reduceByKey(Integer::sum);
    }

    public static Heuristic insertheuristic(String h){
        Heuristic heuristic = null;
        switch (h) {
				case "Q":
					System.out.println("Elegiste busqueda rapida");
					heuristic = new QuickHeuristic();
					break;
				case "R":
					System.out.println("Elegiste busqueda aleatoria");
					heuristic = new RandomHeuristic();
					break;
                default:
                    System.out.println("Error heuristica no detectada");
			}
        return heuristic;
    }

    public static void printall (JavaPairRDD<String, Integer> wordCountRDD, JavaPairRDD<String, Integer> wordClassRDD,
        JavaPairRDD<String, Integer> wordSubclassRDD, JavaPairRDD<String, Integer> wordSubclassTemaRDD) {
        FilePrinter.Printear(wordCountRDD, "Entidad Nombre: ");
        FilePrinter.Printear(wordClassRDD, "Clase: ");
        FilePrinter.Printear(wordSubclassRDD, "Subclase: ");
        FilePrinter.Printear(wordSubclassTemaRDD, "Subclase Tema: ");
    }

    public static void searching (JavaRDD<Article> articletotalRDD, String palabra) {
        JavaRDD<Tuple2<Integer, HashMap<String, Integer>>> IndiceInvertido = articletotalRDD.map(article -> {
            return article.IndiceInvertido();
        });
        List<Tuple2<Integer, HashMap<String, Integer>>> indiceList = IndiceInvertido.collect();
        List<Tuple3<Integer, String, Integer>> aux = new ArrayList<Tuple3<Integer, String, Integer>>();
        for (Tuple2<Integer, HashMap<String, Integer>> tuple : indiceList) {
            Integer id = tuple._1; // Acceder al ID del artículo
            HashMap<String, Integer> indice = tuple._2; // Acceder al índice invertido
            if (indice.containsKey(palabra)) {
                Integer ocurrencias = indice.get(palabra);
                //docu, palabra, ocurrencias
                aux.add(new Tuple3<Integer, String, Integer>(id, palabra, ocurrencias));
            }
        }
        if (aux.isEmpty()) {
            System.out.println("La lista está vacía, no se encontró la palabra deseada en el índice invertido");
        } else {
            Collections.sort(aux, Comparator.comparing(tuple -> tuple._3(), Comparator.reverseOrder()));
            System.out.println("Búsqueda de palabra con índice invertido: \n");
            for (Tuple3<Integer, String, Integer> tuple : aux) {
                System.out.println("Documento: '" + tuple._1() + "'   Palabra: " + tuple._2() + "   Ocurrencias: " + tuple._3());
            }
        }
    }
}