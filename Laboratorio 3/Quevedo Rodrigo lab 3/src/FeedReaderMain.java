import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import feed.Article;
import feed.Feed;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.NamedEntity;
import namedEntity.heuristic.QuickHeuristic;
import namedEntity.heuristic.RandomHeuristic;
import java.io.FileNotFoundException;
import java.util.List;
import httpRequest.httpRequester;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.util.HashMap;
import java.util.Map;
public class FeedReaderMain {
    private static void printHelp() {
        System.out.println("Please, call this program in the correct way: FeedReader [-ne]");
    }

    public static void main(String[] args) {
        System.out.println("************* FeedReader version 1.0 *************");
        String path = "../config/subscriptions.json";
        SubscriptionParser subs = new SubscriptionParser(path);

        if (args.length == 0) {
          //  try {
                for (int m = 0; m < subs.subs.getSubscriptionSize(); m++) {
                    SingleSubscription sg = subs.subs.getSingleSubscription(m);
                    try {
                        if (sg.getUrlType().equals("rss")) {
                            for (int i = 0; i < sg.getUlrParamsSize(); i++) {
                                String link = sg.getFeedToRequest(i);
                                httpRequester httpRequester = new httpRequester();
                                String rssFeed = httpRequester.getFeedRss(link);
                                RssParser rssparser = new RssParser();
                                Feed feed = rssparser.parser(rssFeed);
                                feed.prettyPrint();
                            }
                        } else {
                            System.out.println("Error, este elemento de la lista de urls no es un archivo rss");
                        }
                    } catch (Exception e) {
                        System.err.println("Archivo no encontrado: " + e.getMessage());
                    }
                }
           /*  } catch (FileNotFoundException e) {
                System.err.println("Archivo no encontrado: " + e.getMessage());
            } */
        } else if (args.length == 1) {
            Map<String, Integer> entityOccurrences = new HashMap<>();

            for (int m = 0; m < subs.subs.getSubscriptionSize(); m++) {
                SingleSubscription sg = subs.subs.getSingleSubscription(m);
                if (sg.getUrlType().equals("rss")) {
                    for (int j = 0; j < sg.getUlrParamsSize(); j++) {
                        try {
                            String link = sg.getFeedToRequest(j);
                            httpRequester httpRequester = new httpRequester();
                            String rssFeed = httpRequester.getFeedRss(link);
                            RssParser rssparser = new RssParser();
                            Feed feed = rssparser.parser(rssFeed);
                            Heuristic heuristic = null;

                            switch (args[0]) {
                                case "Q":
                                    System.out.println("Elegiste búsqueda rápida");
                                    heuristic = new QuickHeuristic();
                                    break;
                                case "R":
                                    System.out.println("Elegiste búsqueda aleatoria");
                                    heuristic = new RandomHeuristic();
                                    break;
                                default:
                                    System.out.println("Error en el algoritmo de búsqueda ingresado");
                                    System.exit(1);
                                    break;
                            }

							//usar la heuristica para computar entidades..
							// Configuración de Spark
                            SparkConf conf = new SparkConf().setAppName("FeedReader").setMaster("local[*]");
                            
                            // Crear el contexto de Spark
                            JavaSparkContext sparkContext = new JavaSparkContext(conf);

                            JavaRDD<Article> articleRDD = sparkContext.parallelize(feed.getArticleList());
                            final Heuristic finalHeuristic = heuristic;
							JavaRDD<List<NamedEntity>> namedEntitiesRDD = articleRDD.map(article -> {
										article.computeNamedEntities(finalHeuristic);
										return article.getNamedEntities();
							});

                            namedEntitiesRDD.collect().forEach(namedEntities -> {
                                for (NamedEntity namedEntity : namedEntities) {
                                    String entityName = namedEntity.getName();
                                    int occurrence = entityOccurrences.getOrDefault(entityName, 0);
                                    entityOccurrences.put(entityName, occurrence + 1);
                                }
                            });

                            System.out.println("Entity Occurrences:");
                            for (Map.Entry<String, Integer> entry : entityOccurrences.entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue());
                            }

                            sparkContext.close();
                        } catch (Exception e) {
                            System.err.println("Archivo no encontrado: " + e.getMessage());
                        }
                    }
                } else {
                    System.out.println("Error, este elemento de la lista de urls no es un archivo rss");
                }
            }
        }
    }
}
