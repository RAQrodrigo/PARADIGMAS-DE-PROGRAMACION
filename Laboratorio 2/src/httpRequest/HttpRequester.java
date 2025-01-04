package httpRequest; //el paquete en donde ta

import java.io.BufferedReader; //leer el contenido de un flujo de entrada (stream) de caracteres, como el que puede ser devuelto por una solicitud HTTP.
//import java.io.IOException; //excepción que se lanza cuando se produce una entrada o salida fallida 
import java.io.InputStreamReader; // permite leer los bytes entrantes como caracteres.
import java.net.HttpURLConnection; //permite abrir una conexión HTTP para enviar una solicitud y recibir una respuesta.
import java.net.URL; //clase que representa una URL

/* Esta clase se encarga de realizar efectivamente el pedido de feed 
al servidor de noticias
 * Leer sobre como hacer una http request en java
 * https://www.baeldung.com/java-http-request
 * */
/* Luego realizar la consulta “http request” al servidor del feed. 
El servidor en caso de éxito, devolverá un feed RSS, que es simplemente 
una interfaz (en formato xml) para obtener el contenido de los diferentes 
artículos del feed. Por ejemplo, si queremos obtener el feed de “business” 
del “sitio New Yor Times”, en el navegador ponemos la siguiente url:  
https://rss.nytimes.com/services/xml/rss/nyt/Business.xml 
*/
public class HttpRequester {
	
	public String getFeedRss(String urlFeed){ //puede lanzar una excepcion si hay un problema en la lectura de la respuesta de la solicitud HTTP
		
		String feedRssXml =  null; //variable que contendra el choclo del feed rss
		try{
			URL url = new URL(urlFeed); //crea un objeto de tipo url
			//abre la conexion e indica que es dde tipo GET
			HttpURLConnection conection = (HttpURLConnection) url.openConnection();
			conection.setRequestMethod("GET"); //solicitud

			//obtiene el codigo de respuesta del server
			int answer = conection.getResponseCode();

			if(answer == HttpURLConnection.HTTP_OK) { //si la respuesta es 200, si se realizo la solicitud con exito c:
				//obtiene el contenido del feed rss
				// leamos la respuesta de la solicitud y colóquela en una Cadena de contenido:
				BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream())); //buferes para leer
				String inputLine;
				StringBuffer content = new StringBuffer();
				//lee cada linea del feed rss y la concatena con content hasta que no haya mas contenido
				while((inputLine = in.readLine()) != null) { //mientras haya contenido lee cada linea del feed RSS y la agrega al objeto StringBuffer (content)
					content.append(inputLine);
				}
				in.close(); //cierra el objeto buffer

				feedRssXml = content.toString(); //guarda el contenido del feed rss en la variable

			} else {
				System.out.println("Error en la conexion");
			}
		} catch(Exception e){
			System.out.println("Error al obtener el feed RSS: " + e.getMessage());
			e.printStackTrace();
		}	

		return feedRssXml;
	}
 
	public String getFeedReedit(String urlFeed) {
		String feedRedditJson = null;

			try{
				URL url = new URL(urlFeed); //crea un objeto de tipo url
				//abre la conexion e indica que es dde tipo GET
				HttpURLConnection conection = (HttpURLConnection) url.openConnection();
				conection.setRequestMethod("GET"); //solicitud
	
				//obtiene el codigo de respuesta del server
				int answer = conection.getResponseCode();
	
				if(answer == HttpURLConnection.HTTP_OK) { //si la respuesta es 200, si se realizo la solicitud con exito c:
					//obtiene el contenido del feed rss
					// leamos la respuesta de la solicitud y colóquela en una Cadena de contenido:
					BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream())); //buferes para leer
					String inputLine;
					StringBuffer content = new StringBuffer();
					//lee cada linea del feed rss y la concatena con content hasta que no haya mas contenido
					while((inputLine = in.readLine()) != null) { //mientras haya contenido lee cada linea del feed RSS y la agrega al objeto StringBuffer (content)
						content.append(inputLine);
					}
					in.close(); //cierra el objeto buffer
	
					feedRedditJson = content.toString(); //guarda el contenido del feed rss en la variable
	
				} else {
					System.out.println("Error en la conexion");
				}
			} catch(Exception e){
				System.out.println("Error al obtener el feed RSS: " + e.getMessage());
				e.printStackTrace();
			}	
	
		return feedRedditJson;
	}
}


