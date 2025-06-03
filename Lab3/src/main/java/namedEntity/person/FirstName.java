package namedEntity.person;

import java.util.ArrayList;

public class FirstName {

    private String canonicForm;
    private ArrayList<String> alternativeForms;
    private String origin;


    public FirstName(){
        this.canonicForm = null;
        this.origin = null;
        this.alternativeForms = new ArrayList<>();
    }

    public void setCanonicForm(String lastName){
        this.canonicForm = lastName;
    }

    public String getCanonicForm(){
        return canonicForm;
    }

    public void setOrigin(String origin){
        this.origin = origin;
    }

    public String getOrigin(){
        return origin;
    }

    public void addAlternativeForm(String alt) {
        this.alternativeForms.add(alt);
    }

    public ArrayList<String> getAlternativeForms() {
        return alternativeForms;
    }

    public String getAlternativeForm(int i) {
        if(i < this.alternativeForms.size()){
            return this.alternativeForms.get(i);
        } else {
            return "IndexOutOfBound";
        }
    }
}