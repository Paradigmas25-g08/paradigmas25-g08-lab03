import parser.*;
import subscription.*;
import httpRequest.*;
import feed.*;
import namedEntity.*;
import namedEntity.heuristic.*;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.text.ParseException;
import scala.Tuple2;
import java.util.List;
import java.util.ArrayList;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
//Spark
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.SparkConf;


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

            // Setup Spark
            SparkConf conf = new SparkConf().setAppName("FeedReaderApp").setMaster("local[*]");
            JavaSparkContext sc = new JavaSparkContext(conf);
            List<String> urlsToFetch = new ArrayList<>();
            
            for (int i = 0; i < subs.getSubscriptionsList().size(); i++) {
                
                String url = subs.getSingleSubscription(i).getUrl();
                for (int j=0 ; j<subs.getSingleSubscription(i).getUrlParams().size(); j++){
                    String param = subs.getSingleSubscription(i).getUrlParams(j);
                    
                    String encodedParam = URLEncoder.encode(param, StandardCharsets.UTF_8); 
                    String urlToFetch = String.format(url, encodedParam);
                    
                    urlsToFetch.add(urlToFetch);
                }
                
            }

            JavaRDD<String> urlsRDD = sc.parallelize(urlsToFetch);

            JavaRDD<Feed> feedsRDD = urlsRDD.map(url -> {
                httpRequester requester = new httpRequester();
                String feedRssString = requester.getFeedRss(url);
        
                RssParser rssParser = new RssParser();
                Feed rssFeed = rssParser.getFeed(feedRssString);

                return rssFeed;
            });
            

            List<Feed> feeds = feedsRDD.collect();

            for (Feed feed : feeds) {
                feed.prettyPrint();
            }

            sc.stop();


        } else if (args.length == 1){

            //   Leer el archivo de suscription por defecto;
            // Llamar al httpRequester para obtenr el feed del servidor
            // Llamar al Parser especifico para extrar los datos necesarios por la aplicacion 
            // Llamar al constructor de Feed
            // Llamar a la heuristica para que compute las entidades nombradas de cada articulos del feed
            // LLamar al prettyPrint de la tabla de entidades nombradas del feed.
              
            // -ne=q -ne=r
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

            // Setup Spark
            SparkConf conf = new SparkConf().setAppName("FeedReaderApp").setMaster("local[*]");
            JavaSparkContext sc = new JavaSparkContext(conf);
            List<String> urlsToFetch = new ArrayList<>();
            
            for (int i = 0; i < subs.getSubscriptionsList().size(); i++) {
                
                String url = subs.getSingleSubscription(i).getUrl();
                for (int j=0 ; j<subs.getSingleSubscription(i).getUrlParams().size(); j++){
                    String param = subs.getSingleSubscription(i).getUrlParams(j);
                    
                    String encodedParam = URLEncoder.encode(param, StandardCharsets.UTF_8); 
                    String urlToFetch = String.format(url, encodedParam);
                    
                    urlsToFetch.add(urlToFetch);
                }
                
            }

            JavaRDD<String> urlsRDD = sc.parallelize(urlsToFetch);

            JavaRDD<Feed> feedsRDD = urlsRDD.map(url -> {
                httpRequester requester = new httpRequester();
                String feedRssString = requester.getFeedRss(url);
        
                RssParser rssParser = new RssParser();
                Feed rssFeed = rssParser.getFeed(feedRssString);

                return rssFeed;
            });
            

            List<Feed> feeds = feedsRDD.collect();

            List<Article> allArticles = new ArrayList<>();

            for(Feed f : feeds){
                allArticles.addAll(f.getArticleList());
            }

            JavaRDD<Article> artRDD = sc.parallelize(allArticles);

            JavaRDD<NamedEntity> namedEntityRDD = artRDD.flatMap(art ->{
                art.computeNamedEntities(heuristica);
                return art.getNamedEntityList().iterator();
            });

            JavaPairRDD<NamedEntity, Integer> frequency = namedEntityRDD.mapToPair(ne -> new Tuple2<>(ne, ne.getFrequency()));

            JavaPairRDD<NamedEntity, Integer> counts = frequency.reduceByKey((i1, i2) -> i1 + i2);

            List<Tuple2<NamedEntity, Integer>> output = counts.collect();

            heuristicPrint(output);

            sc.stop();
            

        }else {
            printHelp();
        }
    }

    public static void heuristicPrint(List<Tuple2<NamedEntity, Integer>> output ) {

        System.out.printf(
        "%-30s | %10s | %20s | %-20s%n", 
        "Name", "Frequency", "Category", "Theme"
        );
        System.out.printf(
        "%-30s-+-%10s-+-%-20s%n", 
        "-".repeat(30), "-".repeat(10), "-".repeat(20)
        );
        Counter person = new Counter();
        Counter country = new Counter();
        Counter city = new Counter();
        Counter address = new Counter();
        Counter company = new Counter();
        Counter product = new Counter();
        Counter event = new Counter();
        Counter date = new Counter();
        Counter other = new Counter();

        for (Tuple2<?, ?> tuple : output) {
            NamedEntity e = (NamedEntity) tuple._1();
            System.out.printf(
            "%-30s | %10s | %20s | %-20s%n",
            e.getName(),
            tuple._2(),
            e.getCategory(),
            e.getTheme().getName()
        );


        if(e.getCategory().equals("Person")){
            person.increment();
        }else if(e.getCategory().equals("Company")){
            company.increment();
        }else if(e.getCategory().equals("Country")){
            country.increment();
        }else if(e.getCategory().equals("City")){
            city.increment();
        }else if(e.getCategory().equals("Address")){
            address.increment();
        }else if(e.getCategory().equals("Product")){
            product.increment();
        }else if(e.getCategory().equals("Date")){
            date.increment();
        }else if(e.getCategory().equals("Event")){
            event.increment();
        }else if(e.getCategory().equals("Other")){
            other.increment();
        }

        }

        
        System.out.printf(
        "%-20s | %10s | %10s | %10s | %20s | %10s | %10s | %10s | %-20s%n", 
        "Personas", "Paises", "Ciudades", "Direcciones", "CompaÃ±ias", "Productos", "Eventos", "Fechas", "Otras"
        );
        System.out.printf(
        "%-20s | %10s | %10s | %10s | %20s | %10s | %10s | %10s | %-20s%n", 
        person.getValue(), country.getValue(), city.getValue(), address.getValue(), company.getValue(), product.getValue(), event.getValue(), date.getValue(), other.getValue()
        );

        System.out.println("Cantidad de entidades:" + output.size());
    }
}
