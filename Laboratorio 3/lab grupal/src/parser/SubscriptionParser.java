package parser;

import subscription.Subscription;
import subscription.SingleSubscription;

import java.io.FileNotFoundException;
import java.io.FileReader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class SubscriptionParser {

    public Subscription subs = new Subscription(null);

    public SubscriptionParser(String path) {
        try {
            FileReader reader = new FileReader(path);
            JSONArray jsonArray = new JSONArray(new JSONTokener(reader));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String urls = obj.getString("url");
                String urlTypes = obj.getString("urlType");
                JSONArray urlParamsArray = obj.getJSONArray("urlParams");
                List<String> urlParamss = new ArrayList<String>();
                for (int j = 0; j < urlParamsArray.length(); j++) {
                    urlParamss.add(urlParamsArray.getString(j));
                }
                SingleSubscription ssubs = new SingleSubscription(urls, urlParamss, urlTypes);
                ssubs.setUlrParams(urlParamss.get(i));
                subs.addSingleSubscription(ssubs);
            }
        } catch (FileNotFoundException e) {
            // Manejar la excepción de archivo no encontrado
            e.printStackTrace();
        } catch (IOException e) {
            // Manejar la excepción de entrada/salida
            e.printStackTrace();
        }
    }



	public List<String> getUrls(){
		List<String> urls = new ArrayList<String>();
		
		for (SingleSubscription sg:subs.getSubscriptionsList()) {
			urls.add(sg.getUrl());
		}
		return urls;
	}
	
	public List<List<String>> getParams() {
		List<List<String>> params = new ArrayList<List<String>>();
		for (int i = 0; i < subs.getSubscriptionsList().size(); i++) {
			SingleSubscription sg = subs.getSubscriptionsList().get(i);
			params.add(sg.getUlrParams());
		}
		return params;
	}

	public List<String> getType() {
		List<String> type = new ArrayList<String>();
		
		for (SingleSubscription sg:subs.getSubscriptionsList()) {
			type.add(sg.getUrlType());
		}
		return type;
	}

}
	
	

