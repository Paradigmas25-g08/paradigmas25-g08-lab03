package namedEntity.location;

import namedEntity.*;
import namedEntity.theme.*;

public class City extends NamedEntity {

    Country country;
    String capital;
    int population;
    
    
    public City(String name, int frequency, Theme theme){
        super(name, "City", frequency, theme);
        this.country = null;
        this.capital = null;
        this.population = -1;
    }

    public void setCityPopulation(int population) {
        this.population = population;
    }

    public int getCityPopulation() {
        return population;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry() {
        return country;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCapital() {
        return capital;
    }

    @Override
	public String toString() {
		return "ObjectCity [name=" + getName() + ", frequency=" + getFrequency() + ", country=" + country.getName() + ", capital=" + capital + ", population=" + population + "]";
	}
	public void prettyPrint(){
		System.out.println(this.getName() + " " + this.getFrequency() + " " + this.country.getName() + " " + this.getCapital() + " " +this.getCityPopulation());
	}
}

