package parser;
import java.util.List;
import feed.Article;
import feed.Feed;

//paquetes relacionados con XML
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.io.StringReader;
//import java.net.http.HttpRequest;
import httpRequest.HttpRequester;

import java.util.Date;
import org.xml.sax.InputSource;

/* Esta clase implementa el parser de feed de tipo rss (xml)
 * https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm 
 * */
/* import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; */
/*  Una vez conseguidos la lista de artículos de un feed, van a extraer sólo aquellos atributos del artículo (item) que nos interesa (title, description,pubDate,link ) 
que luego serán mostrados por pantalla en forma legible para el usuario. Para ello van a tener que parsear formato xml con alguna libreria para ello: 
https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
Notar que deben parsear archivos json y xml, por lo tanto, deben tratar de generalizar 
 */

public class RssParser extends GeneralParser {
    

    public Feed parser(String feedRssXML) {

        Feed feed = new Feed("Rss");//crea un objeto de tipo feed  y le pone el nombre del feed
        try {
            
          //crear documentos Builder Factory
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //crea una instancia de DocumentBuilderFactory, que se utiliza para obtener un DocumentBuilder y así poder analizar documentos XML.
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();//La clase DocumentBuilder proporciona un método para analizar documentos XML en objetos de tipo org.w3c.dom.Document. Los objetos Document son 
            //representaciones de los documentos XML y proporcionan un modelo de árbol del documento XML que se puede manipular y procesar mediante diferentes APIs de procesamiento de XML en Java, como SAX, DOM, StAX, entre otros.
            
            InputSource inputSource = new InputSource(new StringReader(feedRssXML)); //crea un objeto inputsourse porque eso es lo que recibe como parametro la siguiente linea, lo que necesita
            Document doc = dBuilder.parse(inputSource); //La llamada al método parse() de la clase DocumentBuilder analiza el XML y devuelve un objeto Document que se puede usar para acceder y manipular los elementos y atributos del documento XML.

            doc.getDocumentElement().normalize(); //se asegurara de que el documento no tenga nodos vacíos, 

            NodeList nList = doc.getElementsByTagName("item"); //obtiene una lista de todos los elementos en el documento doc que tienen la etiqueta "item". En el contexto de un feed RSS, los elementos "item" corresponden a las 
            //entradas individuales del feed, es decir, a los artículos o noticias publicados. La lista nList contendrá todos los nodos con la etiqueta "item" del feed.


            //Este código recorre todos los nodos "item" del documento XML y, para cada uno, extrae el contenido de los elementos mediante la función getElementsByTagName().
            // Luego, crea un objeto Article con los datos obtenidos y lo agrega a la lista de artículos.
            for (int i = 0; i < nList.getLength(); i++) {

                //modificar esta primera parte para que se adapte a la estructura del feed RSS con respecto al archivo feed y article
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode; //Extrae el elemento de la lista de nodos
                    String title = eElement.getElementsByTagName("title").item(0).getTextContent();
                    String text = eElement.getElementsByTagName("description").item(0).getTextContent();//get text content devuelve el contenido de texto de este nodo y sus descendientes
                    Date publicationDate = new Date();
                    // Date publicationDate = getElementsByTagName("pubDate").item(0).getTextContent();
                    String link = eElement.getElementsByTagName("link").item(0).getTextContent();
                   // String description = eElement.getElementsByTagName("description").item(0).getTextContent();
                    Article article = new Article(title, text, publicationDate, link, i+1);
                   feed.addArticle(article);
                }
            }

        } catch (Exception e) {
            System.out.println("Error en la lectura XML");//e.printStackTrace(); //imprime la traza de la pila de excepciones, da el error/cuando no se puede leer el contenido del feed RSS
        }
        return feed; 
    }

}