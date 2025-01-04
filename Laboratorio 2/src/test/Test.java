package test;

import java.util.Date;
import java.util.List;

import feed.Article;
import feed.Feed;
import httpRequest.HttpRequester;
import parser.RssParser;
import parser.SubscriptionParser;
import subscription.SingleSubscription;
import subscription.Subscription;

public class Test {
    public static void test() {

        System.out.println("Test de Article: \n");
        Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  ); 
		a.prettyPrint();

        System.out.println("*************************************************************************************************************** \n");
        System.out.println("Test de Feed: \n");
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

        System.out.println("*************************************************************************************************************** \n");
        System.out.println("Test de HttpRequester: \n");
        HttpRequester httpRequester = new HttpRequester();
		String rssFeed = httpRequester.getFeedRss("https://rss.nytimes.com/services/xml/rss/nyt/Business.xml");
		System.out.println(rssFeed);
        System.out.println("*************************************************************************************************************** \n");
        System.out.println("Test de RssParser: \n");
        HttpRequester httpRequest = new HttpRequester();
        String rssFeedstr = httpRequest.getFeedRss("https://rss.nytimes.com/services/xml/rss/nyt/Business.xml");
        RssParser rssParser = new RssParser();
        Feed feed = rssParser.parser(rssFeedstr);
        System.out.println(feed.getArticleList());
        System.out.println("*************************************************************************************************************** \n");
        System.out.println("Test de SubscriptionParser: \n");
        String path = "../config/subscriptions.json";
		Subscription subscription = SubscriptionParser.parseSubscription(path);
		List<SingleSubscription> subscriptions = subscription.getSubscriptionsList();
		for (SingleSubscription subs : subscriptions) {
			List<String> urlParams = subs.getUlrParams();
			System.out.println("urlParams: " + urlParams);
		}

		System.out.println("*************************************************************************************************************** \n");
		System.out.println("Test de SingleSubscriptionClass: \n");
		SingleSubscription s = new SingleSubscription("https://rss.nytimes.com/services/xml/rss/nyt/%s.xml", null, "rss");
		s.setUlrParams("Business");
		s.setUlrParams("Technology");
		System.out.println(s.getFeedToRequest(0));
		s.prettyPrint();
    }

    public static void main(String[] args) {
        test();
    }
}
