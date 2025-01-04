package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import namedEntity.Category.subcategory.Company;
import namedEntity.Category.subcategory.Country;
import namedEntity.Category.subcategory.Persona;
import namedEntity.Category.subcategory.Entityunknown;
import namedEntity.Tema.Cultura;
import namedEntity.Tema.Deportes;
import namedEntity.Tema.Tema;
import namedEntity.Tema.Unknown;
import namedEntity.Tema.Politica;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.NamedEntity;
import word.Word;

/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article {
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	private List<Word> randomwords = new ArrayList<>();
	
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

	public List<Word> getRandomWordsList() {
    	return randomwords;
	} 
	
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ "]";
	}
	
	public NamedEntity getNamedEntity(String namedEntity) {
		/* int indice = namedEntityList.indexOf(namedEntity);
		if (indice != -1) {
			return namedEntityList.get(indice);
		}
		else {
			return null;
		} */
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){
					return n;
				}
			}
		return null;
	}
	

	public Word getRandomWords(String word) {
		for (Word w : randomwords) {
			if (w.getWord().compareTo(word) == 0) {
				return w;
			}
		}
		return null;
	}

	public Word searchWord (Word searchword){
		if (searchword != null){
			Word prueba = this.getRandomWords(searchword.getWord());
			if (prueba != null) {
				searchword.sumFrecuency(prueba.getFrecuencywd());
			}
		}
		return searchword;
	}
	public Word searchEntity(Word searchentity){
		if (searchentity != null){
			NamedEntity prueba = this.getNamedEntity(searchentity.getWord());
			if(prueba != null){
				searchentity.sumFrecuency(prueba.getFrequency());
			}
		}
		return searchentity;
	}

	public Word searchingwords (Word word,Boolean isentity) {
		 if (isentity) {
			word = this.searchEntity(word);
		}
		else {
			word = this.searchWord(word);
		}
		return word;
	}

	public void pretyPrintEntity(){
		for (int i = 0 ; i < namedEntityList.size() ; ++i) {
			namedEntityList.get(i).prettyPrint();
		}
	}

	public void computeNamedEntities(Heuristic h) {
		String charsToRemove = ".,;:()'!?\\n";
		String text = this.getTitle() + " " + this.getText();
		text = text.replaceAll("[" + charsToRemove + "]", "");

		for (String s : text.split(" ")) {
			if (h.isEntity(s)) {
				NamedEntity ne = this.getNamedEntity(s);
				if (ne == null) {
					String category = h.getCategory(s);
					Tema tematica = null;
					NamedEntity entity = null;

					if (category == null) {
						tematica = new Unknown("Sin tema");
						entity = new Entityunknown(s,tematica);
						this.namedEntityList.add(entity);
						continue;
					}

					tematica = initTema(s, tematica);
					switch (category) {
						case "Person":
							entity = new Persona(s, tematica);
							break;
						case "Company":
							entity = new Company(s, tematica);
							break;
						case "Country":
							entity = new Country(s, tematica);
							break;
					}
					this.namedEntityList.add(entity);
				}
				else {
					ne.incFrequency();
				}
			}
			else {
				Word wd = getRandomWords(s);
				if (wd == null){
					wd = new Word(s,1);
					randomwords.add(wd);
				}
				else {
					wd.incFrequencywd();
				}
			}
		}
	}

	public Tema initTema(String name,Tema tematica){
		switch(name){
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
			case  "Microsoft":
			case "Apple":
			case "Google":
				tematica = new Cultura("Tecnología");
				break;
			case "USA":
			case "Russia":
				tematica = new Cultura("Internacional");
			break;
		}
		return tematica;
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
}


