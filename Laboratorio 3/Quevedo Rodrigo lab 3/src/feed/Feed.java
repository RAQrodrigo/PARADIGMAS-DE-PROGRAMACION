package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*Esta clase modela la lista de articulos de un determinado feed*/
public class Feed {
	String siteName;
	List<Article> articleList;
	
	public Feed(String siteName) {
		super();
		this.siteName = siteName;
		this.articleList = new ArrayList<Article>();
	}

	//devuelve el nombre del sitio
	public String getSiteName(){
		return siteName;
	}

	//actualizar el valor del atributo siteName en la instancia actual de la clase.
	public void setSiteName(String siteName){
		this.siteName = siteName;
	}
	
	//método público en una clase en Java que devuelve una lista de objetos de tipo Article
	public List<Article> getArticleList(){
		return articleList;
	}
	
	//actualizar el valor del atributo articleList en la instancia actual de la clase.
	public void setArticleList(List<Article> articleList){
		this.articleList = articleList;
	}
	
	//agrega un objeto de tipo Article a la lista de artículos
	public void addArticle(Article a){
		this.getArticleList().add(a);
	}
	
	//devuelve un objeto de tipo Article de la lista de artículos
	public Article getArticle(int i){
		return this.getArticleList().get(i);
	}
	
	//devuelve el número de artículos en la lista de artículos
	public int getNumberOfArticles(){
		return this.getArticleList().size();
	}
	
	// devuelve una cadena que contiene el nombre del sitio web (siteName) y una lista de artículos (articleList).
	@Override
	public String toString(){
		return "Feed [siteName=" + getSiteName() + ", articleList=" + getArticleList() + "]";
	}
	
//imprime el articulo
	public void prettyPrint(){ 
		for (Article a: this.getArticleList()){
			a.prettyPrint();
		}
		
	}
	
	public static void main(String[] args) {
		  Article a1 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  Article a2 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
				  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
				  new Date(),
				  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
				  );
		  
		  Article a3 = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
				  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
				  new Date(),
				  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
				  );
		  
		  Feed f = new Feed("nytimes");
		  f.addArticle(a1);
		  f.addArticle(a2);
		  f.addArticle(a3);

		  f.prettyPrint();
		  
	}
	
}
