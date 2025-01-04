package Modularizar;

import org.apache.spark.api.java.JavaRDD;

import feed.Feed;
import httpRequest.HttpRequester;
import namedEntity.heuristic.NamedEntity;
import parser.RssParser;
import subscription.SingleSubscription;
import feed.Article;
import java.util.List;
import java.util.ArrayList;

public class ProcessSubscriptionsAndPrint {

    public static void processSubscription(SingleSubscription sg) {
        if (sg.getUrlType().equals("rss")) {
            for (int i = 0; i < sg.getUlrParamsSize(); i++) {
                String enlace = sg.getFeedToRequest(i);
                HttpRequester httpRequester = new HttpRequester();
                String rssFeed = httpRequester.getFeedRss(enlace);
                RssParser rssParser = new RssParser();
                Feed feed = rssParser.parser(rssFeed);
                feed.prettyPrint();
            }
        }
    }

    public static JavaRDD<Article> processSubscriptionsArticles(JavaRDD<SingleSubscription> subscriptionRDD) {
    return subscriptionRDD.flatMap(sg -> {
        List<Article> articles = new ArrayList<>();
        if (sg.getUrlType().equals("rss")) {
            for (int j = 0; j < sg.getUlrParamsSize(); j++) {
                String link = sg.getFeedToRequest(j);
                HttpRequester httpRequester = new HttpRequester();
                String rssFeed = httpRequester.getFeedRss(link);
                RssParser rssparser = new RssParser();
				Feed feed = rssparser.parser(rssFeed);
                articles.addAll(feed.getArticleList());
            }
        }
        return articles.iterator();
    });
    }

    public static void printEntities(JavaRDD<List<NamedEntity>> transformedRDD) {
    transformedRDD.foreach(entityList -> {
        for (NamedEntity entity : entityList) {
            entity.prettyPrint();
        }
    });
}
    
}
