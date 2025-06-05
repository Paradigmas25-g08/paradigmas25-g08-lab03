package namedEntity;

import java.io.Serializable;
import namedEntity.theme.Theme;
import java.util.Objects;


/*Esta clase modela la nocion de entidad nombrada*/

public class NamedEntity implements Serializable {
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
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NamedEntity that = (NamedEntity) obj;
        return Objects.equals(this.name, that.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
	
}



