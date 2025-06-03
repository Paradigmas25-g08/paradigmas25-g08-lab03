package namedEntity;

import namedEntity.theme.*;

public class Product extends NamedEntity{

    private String comercial;
    private String producer;

    public Product(String name, int frequency, Theme theme){
        super(name, "Product", frequency, theme);
        this.comercial = "";
        this.producer = "";
    }

    public String getComercial() {
		return comercial;
	}

	public void setComercial(String comercial) {
		this.comercial = comercial;
	}

	public String getProducer() {
		return producer;
	}

	public void setCategory(String producer) {
		this.producer = producer;
	}

    @Override
	public String toString() {
		return "ObjectProduct [name=" + name + ", frequency=" + frequency + ", comercial=" + comercial + " , producer=" + producer +"]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency() + " " + this.getComercial() + " " + this.getProducer());
	}
    
}
