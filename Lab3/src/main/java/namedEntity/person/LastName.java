package namedEntity.person;

public class LastName {

    private String canonicForm;
    private String origin;


    public LastName(){
        this.canonicForm = null;
        this.origin = null;
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
}