package namedEntity;

import namedEntity.theme.Theme;

/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity {
	protected String name;
	protected String category; 
	protected int frequency;
	protected Theme theme;
	
	public NamedEntity(String name, String category, int frequency, Theme theme) {
		super();
		this.name = name;
		this.category = category;
		this.frequency = frequency;
		this.theme = theme;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void incFrequency() {
		this.frequency++;
	}

	public Theme getTheme(){
		return theme;
	}

	@Override
	public String toString() {
		return "ObjectNamedEntity [name=" + name + ", frequency=" + frequency + "]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency());
	}
	
	
}



