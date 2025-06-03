import parser.*;
import subscription.*;
import httpRequest.*;
import feed.*;
import namedEntity.heuristic.*;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.text.ParseException;


public class FeedReaderMain {

    private static void printHelp(){
        System.out.println("Please, call this program in correct way: FeedReader [-ne=h] where h can be: r for RandomHeuristic or q for QuickHeuristic");
    }

    public static void main(String[] args) throws IOException, URISyntaxException, ParserConfigurationException, SAXException, ParseException{
        System.out.println("** FeedReader version 1.0 **");
        if (args.length == 0) {

            /* 
            Leer el archivo de suscription por defecto;
            Llamar al httpRequester para obtenr el feed del servidor
            Llamar al Parser especifico para extrar los datos necesarios por la aplicacion 
            Llamar al constructor de Feed
            LLamar al prettyPrint del Feed para ver los articulos del feed en forma legible y amigable para el usuario
            */

            SubscriptionParser subParser = new SubscriptionParser();
            Subscription subs = subParser.FileParser("./src/main/config/subscriptions.json");
            
            for (int i = 0; i < subs.getSubscriptionsList().size(); i++) {
                if(subs.getSingleSubscription(i).getUrlType().equals("rss")){
                    String Url = subs.getSingleSubscription(i).getUrl();
                    for (int j=0 ; j<subs.getSingleSubscription(i).getUrlParams().size(); j++){
                        String Param = subs.getSingleSubscription(i).getUrlParams(j);
                    
                        httpRequester requester = new httpRequester();
                        String feedRssString = requester.getFeedRss(Url, Param);
                
                        RssParser rssParser = new RssParser();
                        Feed rssFeed = rssParser.getFeed(feedRssString);
                        
                        rssFeed.prettyPrint();
                    }
                } else {
                    System.out.println("Caso reddit");
                }
            
            }






        } else if (args.length == 1){

            /* 
            Leer el archivo de suscription por defecto;
            Llamar al httpRequester para obtenr el feed del servidor
            Llamar al Parser especifico para extrar los datos necesarios por la aplicacion 
            Llamar al constructor de Feed
            Llamar a la heuristica para que compute las entidades nombradas de cada articulos del feed
            LLamar al prettyPrint de la tabla de entidades nombradas del feed.
             */
            //-ne=q -ne=r
            SubscriptionParser subParser = new SubscriptionParser();
            Subscription subs = subParser.FileParser("./src/main/config/subscriptions.json");
            Heuristic heuristica;
            if(args[0].equals("-ne=q")){
                heuristica = new QuickHeuristic();
            }else if(args[0].equals("-ne=r")){
                heuristica = new RandomHeuristic();
            } else {
                printHelp();
                return ;
            }

            for (int i = 0; i < subs.getSubscriptionsList().size(); i++) {
                if(subs.getSingleSubscription(i).getUrlType().equals("rss")){
                    String Url = subs.getSingleSubscription(i).getUrl();
                    for (int j=0 ; j<subs.getSingleSubscription(i).getUrlParams().size(); j++){
                        String Param = subs.getSingleSubscription(i).getUrlParams(j);
                    
                        httpRequester requester = new httpRequester();
                        String feedRssString = requester.getFeedRss(Url, Param);
                
                        RssParser rssParser = new RssParser();
                        Feed rssFeed = rssParser.getFeed(feedRssString);

                        for (Article c : rssFeed.getArticleList()){
                            c.computeNamedEntities(heuristica);
                        }
                        
                        rssFeed.heuristicPrint();
                    }
                } else {
                    System.out.println("Caso reddit");
                }
            }
            
            

        }else {
            printHelp();
        }
    }

}

