package namedEntity;

import namedEntity.theme.*;


public class Event extends NamedEntity{
    private String canonicForm;
    private Date1 date;
    private String recurrent;

    public Event(String name, int frequency, Theme theme){
        super(name, "Event", frequency, theme);
        this.canonicForm = null;
        this.date = null;
        this.recurrent = null;
    }
    
    public String getCanonicForm() {
		return canonicForm;
	}

	public void setCcanonicForm(String canonicForm) {
		this.canonicForm = canonicForm;
	}

	public Date1 getDate() {
		return date;
	}

	public void setDate(Date1 date) {
		this.date = date;
	}

    public String getRecurrent() {
		return recurrent;
	}

	public void setRecurrent(String recurrent) {
		this.recurrent = recurrent;
	}
    
    @Override
	public String toString() {
		return "ObjectProduct [name=" + name + ", frequency=" + frequency + ", canonicForm=" + canonicForm + ", date=" + date + ", recurrent=" + recurrent + "]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency() + " " + this.getCanonicForm() + " " + this.getDate() + " " +this.getRecurrent());
	}
}
