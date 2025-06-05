package namedEntity.heuristic;

import java.io.Serializable;
import java.util.Map;

public abstract class Heuristic implements Serializable {

    private static Map<String, String> categoryMap = Map.of(
            "Microsoft", "Company", 
            "Apple", "Company", 
            "Google", "Company",
            "Musk", "Person",
            "Biden", "Person",
            "Trump", "Person",
            "Messi", "Person",
            "Federer", "Person",
            "USA", "Country",
            "Russia", "Country"
            );
    private static Map<String, String> themeMap = Map.of(
            "Microsoft", "Technology", 
            "Apple", "Technology", 
            "Google", "Technology",
            "Musk", "International",
            "Biden", "International",
            "Trump", "International",
            "Messi", "Football",
            "Federer", "Tenis",
            "USA", "International",
            "Russia", "International"
            );

    public String getCategory(String entity){
        return categoryMap.get(entity);
    }
    public String getTheme(String entity){
        return themeMap.get(entity);
    }

    public abstract boolean isEntity(String word);

}