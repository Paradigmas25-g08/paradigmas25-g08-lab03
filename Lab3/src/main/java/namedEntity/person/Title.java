package namedEntity.person;

public class Title {
    private String canonicForm;
    private String profesional;

    public Title() {
        this.canonicForm = null;
        this.profesional = null;
    }

    public void setCanonicForm(String title){
        this.canonicForm = title;
    }

    public String getCanonicForm(){
        return canonicForm;
    }

    public void setProfesional(String profesional){
        this.profesional = profesional;
    }

    public String getProfesional(){
        return profesional;
    }

    
}