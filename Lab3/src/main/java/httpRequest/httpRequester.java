package httpRequest;

import java.net.HttpURLConnection;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;
import java.net.URISyntaxException;

/* Esta clase se encarga de realizar efectivamente el pedido de feed al servidor de noticias
 

    Leer sobre como hacer una http request en java,
    https://www.baeldung.com/java-http-request,
 */


public class httpRequester {

    public String getFeedRss(String finalUrl) throws URISyntaxException, MalformedURLException, IOException, ProtocolException{

        //Creamos la conexion y realizamos la peticion a traves de ella. 
        String feedRssXml = null;
        URL url = new URI(finalUrl).toURL();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        int status = con.getResponseCode();

        Reader streamReader = null;

        //Chequeamos el estatus de la respuesta
        if(status > 299){
            streamReader = new InputStreamReader(con.getErrorStream());
        }else{
            streamReader = new InputStreamReader(con.getInputStream());
        }

        //Comenzamos leemos la respuesta y la asignamos a content
        BufferedReader in = new BufferedReader(streamReader);
        String inputLine = null;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        feedRssXml = content.toString();

        con.disconnect();

        return feedRssXml;
    }

    public String getFeedReedit(String urlFeed) {
         String feedReeditJson = null;
         return feedReeditJson;
     }

}