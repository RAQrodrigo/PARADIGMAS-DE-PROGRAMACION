package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */

public class SubscriptionParser {
	
public static Subscription parseSubscription(String path) {
        Subscription subscription = new Subscription(null);
        try {
            FileReader reader = new FileReader(path);
            JSONArray jsonArray = new JSONArray(new JSONTokener(reader));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String urls = obj.getString("url");
                String urlTypes = obj.getString("urlType");
                JSONArray urlParamsArray = obj.getJSONArray("urlParams");
                List<String> urlParamsList = new ArrayList<String>();
                for (int j = 0; j < urlParamsArray.length(); j++) {
                    urlParamsList.add(urlParamsArray.getString(j));
                }
                SingleSubscription singleSubscription = new SingleSubscription(urls, urlParamsList, urlTypes);
                singleSubscription.setUlrParams(urlParamsList.get(i));
                subscription.addSingleSubscription(singleSubscription);
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return subscription;
    }
}
	
	

