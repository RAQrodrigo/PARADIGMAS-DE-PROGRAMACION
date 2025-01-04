import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.JavaPairRDD; 
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import Modularizar.ProcessSubscriptionsAndPrint;
import Modularizar.FilePrinter;
import feed.Article;
import namedEntity.heuristic.Heuristic;
import namedEntity.heuristic.NamedEntity;

import parser.SubscriptionParser;
import subscription.SingleSubscription;


// javac -cp .:../lib/* FeedReaderMain.java
//javac  -proc:none -cp .:../lib/* FeedReaderMain.java Q/R Titanic (o aluna otra palabra que se desee buscar)
//java  -cp .:../lib/* FeedReaderMain

public class FeedReaderMain {

	private static void printHelp(){
		System.out.println("Please, call this program in correct way: FeedReader [-ne]");
	}
	
	public static void main(String[] args) {
		System.out.println("************* FeedReader version 2.0 *************");
		PropertyConfigurator.configure(FeedReaderMain.class.getResource("/log4j.properties"));

		SparkSession spark = SparkSession.builder()
                .appName("SparkParallelProcessingExample")
                .master("local")
                .getOrCreate();

		String path = "config/subscriptions.json";

		SubscriptionParser subs = new SubscriptionParser(path);
		List<SingleSubscription> subscriptionList = subs.subs.getSubscriptionsList(); // Obtener la lista de suscripciones

		JavaSparkContext sparkContext = JavaSparkContext.fromSparkContext(spark.sparkContext());
        JavaRDD<SingleSubscription> subscriptionRDD = sparkContext.parallelize(subscriptionList);

		if (args.length == 0) {
			subscriptionRDD.foreach(sg -> ProcessSubscriptionsAndPrint.processSubscription(sg));
			spark.stop();

    	} else if (args.length >= 1){
			Heuristic heuristic = FilePrinter.insertheuristic(args[0]);
			Boolean search = (args.length == 2);
			List<Article> articless = new ArrayList<>();
			JavaRDD<Article> articleRDD = ProcessSubscriptionsAndPrint.processSubscriptionsArticles(subscriptionRDD);
			List<Article> allArticles = articleRDD.collect();
			articless.addAll(allArticles);

			SparkSession spark2 = SparkSession.builder()
					.appName("SparkParallelProcessingExample")
					.master("local")
					.getOrCreate();
			JavaSparkContext sparkContext2 = JavaSparkContext.fromSparkContext(spark2.sparkContext());
			JavaRDD<Article> articletotalRDD = sparkContext2.parallelize(articless);

			if (search) {
				String palabra = args[1];
				FilePrinter.searching(articletotalRDD, palabra);
			}

			Heuristic localHeuristic = heuristic;
			
			JavaRDD<List<NamedEntity>> transformedRDD = articleRDD.map(article -> {
			return article.computeNamedEntities(localHeuristic);
			});

			JavaPairRDD<String, Integer> wordCountRDD = FilePrinter.generatePairRDD(transformedRDD,"Name");
			JavaPairRDD<String, Integer> wordClassRDD = FilePrinter.generatePairRDD(transformedRDD,"Class");
			JavaPairRDD<String, Integer> wordSubclassRDD = FilePrinter.generatePairRDD(transformedRDD,"Subclass");
			JavaPairRDD<String, Integer> wordSubclassTemaRDD = FilePrinter.generatePairRDD(transformedRDD,"ClassTema");
			FilePrinter.printall(wordCountRDD, wordClassRDD, wordSubclassRDD, wordSubclassTemaRDD);

			ProcessSubscriptionsAndPrint.printEntities(transformedRDD);

			spark2.stop();
			sparkContext2.stop();
		}
	}
}

			