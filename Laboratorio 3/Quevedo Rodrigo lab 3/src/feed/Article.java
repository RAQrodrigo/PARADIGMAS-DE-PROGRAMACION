package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.Category.subcategory.Company;
import namedEntity.Category.subcategory.Country;
import namedEntity.Category.subcategory.Persona;
import namedEntity.Tema.Cultura;
import namedEntity.Tema.Deportes;
import namedEntity.Tema.Tema;
import namedEntity.Tema.Politica;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.NamedEntity;
import java.io.Serializable;

/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article implements Serializable {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
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

	public List<NamedEntity> getNamedEntities(){
		return namedEntityList;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	
	//busca y devuelve un objeto de tipo NamedEntity de la lista de NamedEntity, si no lo encuentra devuelve null
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){
				return n;
			}
		}
		return null;
	}

	
	public void pretyprintentity(){
		for (int i = 0 ; i < namedEntityList.size() ; ++i) {
			namedEntityList.get(i).prettyPrint();
		}
		
	}
	
	public void computeNamedEntities(Heuristic h)
	{
		String text = this.getTitle() + " " +  this.getText();  
			
		String charsToRemove = ".,;:()'!?\n";
		for (char c : charsToRemove.toCharArray()) {
			text = text.replace(String.valueOf(c), "");
		}
			
			for (String s : text.split(" ")) {
			if (h.isEntity(s)) {
				NamedEntity ne = this.getNamedEntity(s);
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
									entity = new Persona(s, tematica);
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
									entity = new Company(s, tematica);
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
									entity = new Country(s, tematica);
								}
								break;
					}
					if (entity != null) {
						this.namedEntityList.add(entity);
					}
				}
		}
	}

	}

	//imprime el contenido del articulo
	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + this.getText());
		System.out.println("**********************************************************************************************");
		
	}
	
	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}



