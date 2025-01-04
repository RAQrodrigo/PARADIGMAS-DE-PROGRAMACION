import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import subscription.Subscription;
import feed.Article;
import feed.Feed;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;

import java.util.List;
import httpRequest.HttpRequester;
import word.Word;

//para compilar:  java -cp .:lib/json-20230227.jar FeedReaderMain R

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader Q/R [word](Q OR R is the algorithm to use, word is optional)");
	}
	
	public static void main(String[] args) {
		System.out.println("************* FeedReader version 1.0 *************");

		String path = "../config/subscriptions.json";
		Subscription subscription = SubscriptionParser.parseSubscription(path);

		if (args.length == 0) {
		 	for (int m = 0; m < subscription.getSubscriptionSize(); m++) {
                SingleSubscription sg = subscription.getSingleSubscription(m);
                if (sg.getUrlType().equals("rss")) {
                    for (int i = 0; i < sg.getUlrParamsSize(); i++) {
                        String link = sg.getFeedToRequest(i);
                        HttpRequester httpRequester = new HttpRequester();
                        String rssFeed = httpRequester.getFeedRss(link);
                        RssParser rssparser = new RssParser();
                        Feed feed = rssparser.parser(rssFeed);
                        feed.prettyPrint();
                    }
                }
            }
		}else if (args.length >= 1){
            Boolean search = (args.length == 2);
            Boolean isentity = false;
            Word searching = null;
            Heuristic heuristic = null;

			switch (args[0]) {
				case "Q":
					System.out.println("Elegiste busqueda rapida");
					heuristic = new QuickHeuristic();
					break;
				case "R":
					System.out.println("Elegiste busqueda aleatoria");
					heuristic = new RandomHeuristic();
					break;
				default:
					System.out.println("Error en el algoritmo de búsqueda ingresado");
					System.exit(1);
					break;
			}
			
			if (search) {
                searching = new Word(args[1], 0);
                isentity = heuristic.isEntity(searching.getWord());
            }

			for (int m = 0; m < subscription.getSubscriptionSize(); m++) {
                SingleSubscription sg = subscription.getSingleSubscription(m);
                if (sg.getUrlType().equals("rss")) {
                    for (int j = 0; j < sg.getUlrParamsSize(); j++) {
                        String link = sg.getFeedToRequest(j);
                        HttpRequester httpRequester = new HttpRequester();
                        String rssFeed = httpRequester.getFeedRss(link);
                        RssParser rssparser = new RssParser();
                        Feed feed = rssparser.parser(rssFeed);

                        List<Article> articles = feed.getArticleList();

                        for (int i = 0; i < feed.getNumberOfArticles(); i++) {
                            articles.get(i).computeNamedEntities(heuristic);
                            articles.get(i).pretyPrintEntity();
          
                            if (search){
                                    searching = articles.get(i).searchingwords(searching, isentity);
                            }
                        }
                    }
                } 
			}
            if (search){
                System.out.println("Su busqueda: " + searching.getWord() + " aparece: " + searching.getFrecuencywd() + " Veces");
            }
		} else {
            printHelp();
        }
	}
}

