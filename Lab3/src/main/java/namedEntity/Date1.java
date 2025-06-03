package namedEntity;
import java.util.Date;
import namedEntity.theme.*;

public class Date1 extends NamedEntity{
    Date precise;
    String canonicForm;
    public Date1(String name, int frequency, Theme theme){
        super(name, "Date", frequency, theme);
        this.precise = null;
        this.canonicForm = null;

    }
    
    public void setPrecise(Date precise) {
        this.precise = precise;
    }

    public Date getPrecise() {
        return precise;
    }

    public void setCanonicForm(String canonicForm) {
        this.canonicForm = canonicForm;
    }

    public String getCanonicForm() {
        return canonicForm;
    }

    @Override
    public String toString() {
        return "Other[name=" + name + ", frequency=" + frequency + " precise="+ precise +" canonicForm="+ canonicForm+"]";
    }
    public void prettyPrint(){
        System.out.println(this.getName() + " " + this.getFrequency() + " " + this.getPrecise()+" "+ this.getCanonicForm());
    }
}
