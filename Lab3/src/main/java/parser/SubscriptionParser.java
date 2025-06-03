package parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import subscription.SingleSubscription;
import subscription.Subscription;
import org.json.JSONObject;
import org.json.JSONArray;

/*
 

    Esta clase implementa el parser del  archivo de suscripcion (json),
    Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html,
*/


public class SubscriptionParser extends GeneralParser{

    public Subscription FileParser(String jsonFilePath) throws IOException {

        String jsonData = new String (Files.readAllBytes(Paths.get(jsonFilePath)));
        Subscription subscriptionList = new Subscription(null);
        JSONArray jsonArray = new JSONArray(jsonData);

        for(int i = 0; i < jsonArray.length(); i++){

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String url = jsonObject.getString("url");
            JSONArray paramsArray  = jsonObject.getJSONArray("urlParams");
            ArrayList <String> urlParams = new ArrayList <>();
            for (int j = 0; j < paramsArray.length(); j++){
                String urlParam = paramsArray.getString(j);
                urlParams.add(urlParam);
            }

            String urlType = jsonObject.getString("urlType");
            SingleSubscription newSub = new SingleSubscription(url, urlParams, urlType);

            subscriptionList.addSingleSubscription(newSub);
        }

        return subscriptionList;
    }
}