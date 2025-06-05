package namedEntity.theme;
import java.io.Serializable;

public class Theme implements Serializable{
    String name;
    
    public Theme(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}
