package namedEntity.person;

import namedEntity.*;
import namedEntity.theme.*;
import java.util.Optional;

public class Person extends NamedEntity {
    private int id;
	private LastName lastName;
	private FirstName firstName;
	private Title title;

    public Person(String name, int frequency, Theme theme) {
		super(name, "Person", frequency, theme);
        this.id = 1;
		this.lastName = new LastName();
		this.firstName = new FirstName();
		this.title = new Title();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LastName getLastName() {
		return lastName;
	}

	public void setLastName(String canon,Optional<String> origin){
		this.lastName.setCanonicForm(canon);
		this.lastName.setOrigin(origin.orElse(""));
	}

	public FirstName getFirstName() {
		return firstName;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(String canon, Optional<String> pro) {
		this.title.setCanonicForm(canon);
		this.title.setProfesional(pro.orElse(""));
	}

    @Override
	public String toString() {
		return "Person [id="+ id +" name="+ super.name +" frequency="+ super.frequency +"]";
	}
    @Override
	public void prettyPrint(){
		System.out.println(this.getLastName() + " " + this.getFirstName()+ " " + this.getTitle());
	}
}