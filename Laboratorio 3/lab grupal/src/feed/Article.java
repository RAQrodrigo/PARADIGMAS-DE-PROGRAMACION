package feed;
import java.util.*;

import namedEntity.Category.subcategory.Company;
import namedEntity.Category.subcategory.Country;
import namedEntity.Category.subcategory.Persona;
import namedEntity.Tema.Cultura;
import namedEntity.Tema.Deportes;
import namedEntity.Tema.Tema;
import namedEntity.Tema.Politica;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.NamedEntity;
import scala.Tuple2;


import java.io.Serializable;
/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */
//import org.apache.spark.api.java.JavaPairRDD;
//import scala.Tuple4;


public class Article implements Serializable {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	private Integer id;
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link, Integer id) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
		this.id = id;
	}

	public Tuple2<Integer, HashMap<String, Integer>> IndiceInvertido() {
		String charsToRemove = ".,;:()'!?\\n";
		String text = this.getTitle() + " " + this.getText();
		text = text.replaceAll("[" + charsToRemove + "]", "");
	
		String[] palabras = text.split(" ");
		HashMap<String, Integer> ocurrencias = new HashMap<>();
	
		for (String palabra : palabras) {
			ocurrencias.put(palabra, ocurrencias.getOrDefault(palabra, 0) + 1);
		}
	
		Tuple2<Integer, HashMap<String, Integer>> tupla = new Tuple2<>(this.getId(), ocurrencias);
		return tupla;
	}
	

	//devuelve el titulo del articulo
	public String getTitle() {
		return title;
	}
	//setea el titulo del articulo
	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	public Integer getId(){
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}

	public List<NamedEntity> getNamedEntityList(){
		return namedEntityList;
	}
	
	//busca y devuelve un objeto de tipo NamedEntity de la lista de NamedEntity, si no lo encuentra devuelve null
	public NamedEntity getNamedEntity(String namedEntity) {
		int indice = namedEntityList.indexOf(namedEntity);
		if (indice != -1) {
			return namedEntityList.get(indice);
		} else {
			return null;
		}
	}
	
	public void pretyprintentity(){
		for (int i = 0 ; i < namedEntityList.size(); ++i) {
			namedEntityList.get(i).prettyPrint();
		}
	}

	//computa las entidades nombradas del articulo y las agrega a la lista de NamedEntity
	public List<NamedEntity> computeNamedEntities(Heuristic h) {
		List<NamedEntity> tempList = new ArrayList<>();
		String charsToRemove = ".,;:()'!?\\n";
		String text = this.getTitle() + " " + this.getText();
		text = text.replaceAll("[" + charsToRemove + "]", "");


		for (String s : text.split(" ")) {
			if (h.isEntity(s)) {
				NamedEntity ne = this.getNamedEntity(s);
				Integer id = this.getId();
				if (ne == null) {
					String category = h.getCategory(s);
					if (category == null) {
						continue;
					}
					Tema tematica = null;
					NamedEntity entity = null;
					switch (category) {
						case "Person":
							switch (s) {
								case "Messi":
									tematica = new Deportes("Fútbol");
									break;
								case "Biden":
								case "Trump":
								case "Musk":
									tematica = new Politica("Internacional");
									break;
								case "Federer":
									tematica = new Deportes("Tennis");
									break;
								}
								if (tematica != null) {
									entity = new Persona(s, tematica, id);
								}
							break;
						case "Company":
								switch (s) {
									case  "Microsoft":
									case "Apple":
									case "Google":
										tematica = new Cultura("Tecnología");
										break;
								}
								if (tematica != null) {
									entity = new Company(s, tematica, id);
								}
								break;
						case "Country":
								switch (s) {
									case "USA":
									case "Russia":
										tematica = new Cultura("Internacional");
									break;
								}
								if (tematica != null) {
									entity = new Country(s, tematica, id);
								}
								break;
					}
					if (entity != null) {
						this.namedEntityList.add(entity);
						tempList.add(entity);
					}
				}
				else {
					ne.incFrequency();
				}
			}
		}
		return tempList;
	}


	//imprime el contenido del articulo
	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Id: " + this.getId());
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + this.getText());
		System.out.println("**********************************************************************************************");
		
	}
	
}

