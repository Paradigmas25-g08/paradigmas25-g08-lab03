package feed;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.io.Serializable;

import namedEntity.NamedEntity;
import namedEntity.person.*;
import namedEntity.*;
import namedEntity.location.*;
import namedEntity.heuristic.Heuristic;
import namedEntity.theme.*;
import namedEntity.theme.Culture.*;
import namedEntity.theme.Politic.*;
import namedEntity.theme.Sports.*;


/*Esta clase modela el contenido de un articulo (ie, un item en el caso del rss feed) */

public class Article implements Serializable{
	private String title;
	private String text;
	private Date publicationDate;
	private String link;
	
	private List<NamedEntity> namedEntityList = new ArrayList<NamedEntity>();
	
	
	public Article(String title, String text, Date publicationDate, String link) {
		super();
		this.title = title;
		this.text = text;
		this.publicationDate = publicationDate;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<NamedEntity> getNamedEntityList() {
		return namedEntityList;
	}
	
	@Override
	public String toString() {
		return "Article [title=" + title + ", text=" + text + ", publicationDate=" + publicationDate + ", link=" + link
				+ " namedEntityList " + namedEntityList + "]";
	}
	
	
	
	public NamedEntity getNamedEntity(String namedEntity){
		for (NamedEntity n: namedEntityList){
			if (n.getName().compareTo(namedEntity) == 0){				
				return n;
			}
		}
		return null;
	}
	
	public void computeNamedEntities(Heuristic h){
		String text = this.getTitle() + " " +  this.getText();  
			
		String charsToRemove = ".‘,$;%-:’()'“”!?\n";
		for (char c : charsToRemove.toCharArray()) {
			text = text.replace(String.valueOf(c), "");
		}
			
		for (String s: text.split(" ")) {
			if (h.isEntity(s)){
				Theme theme = this.newTheme(h.getTheme(s));
				String category = h.getCategory(s);
				if(category!=null && category.equals("Company")) {

					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Company org = new Company(s, 1, theme);
						org.setCanonicForm(s);
						this.namedEntityList.add(org);
					}
				} else if (category!=null && category.equals("Person")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Person per = new Person(s, 1, theme);
						per.setLastName(s, Optional.empty());
						this.namedEntityList.add(per);
					}
				} else if(category!=null && category.equals("Country")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Country coun = new Country(s, 1, theme);
						this.namedEntityList.add(coun);
					}
				} else if(category!=null && category.equals("City")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						City city = new City(s, 1, theme);
						this.namedEntityList.add(city);
					}
				} else if(category!=null && category.equals("Address")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Address ads = new Address(s, 1, theme);
						this.namedEntityList.add(ads);
					}
				} else if(category!=null && category.equals("Product")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Product pdt = new Product(s, 1, theme);
						this.namedEntityList.add(pdt);
					}
				} else if(category!=null && category.equals("Event")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Event evt = new Event(s, 1, theme);
						this.namedEntityList.add(evt);
					}
				} else if(category!=null && category.equals("Date")) {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Date1 dt = new Date1(s, 1, theme);
						this.namedEntityList.add(dt);
					}
				} else {


					if(getNamedEntity(s) != null){
						getNamedEntity(s).incFrequency();
					} else {
						Other oth = new Other(s, 1, theme);
						this.namedEntityList.add(oth);
					}
				}
			}
		} 
	}

	private Theme newTheme(String theme){
		if(theme != null){
			if(theme.equals("Football")){
				return new Futbol();
			} else if(theme.equals("Formula1")){
				return new Formula1();
			} else if(theme.equals("Tenis")){
				return new Tenis();
			} else if(theme.equals("Basquetball")){
				return new Basquet();
			} else if(theme.equals("OtherSport")){
				return new OtrosDeportes();
			} else if(theme.equals("International")){
				return new Internacional();
			} else if(theme.equals("National")){
				return new Nacional();
			} else if(theme.equals("OtherPolitic")){
				return new OtherPolitic();
			} else if(theme.equals("Cinema")){
				return new Cine();
			} else if(theme.equals("Music")){
				return new Musica();
			} else if(theme.equals("OtherCulture")){
				return new OtrosCultura();
			} else {
				return new Othertheme();	
			}
		}else {
			return new Othertheme();
		}
	}

	public void prettyPrint() {
		System.out.println("**********************************************************************************************");
		System.out.println("Title: " + this.getTitle());
		System.out.println("Publication Date: " + this.getPublicationDate());
		System.out.println("Link: " + this.getLink());
		System.out.println("Text: " + this.getText());
		System.out.println("**********************************************************************************************");
		
	}
	
	public static void main(String[] args) {
		  Article a = new Article("This Historically Black University Created Its Own Tech Intern Pipeline",
			  "A new program at Bowie State connects computing students directly with companies, bypassing an often harsh Silicon Valley vetting process",
			  new Date(),
			  "https://www.nytimes.com/2023/04/05/technology/bowie-hbcu-tech-intern-pipeline.html"
			  );
		 
		  a.prettyPrint();
	}
	
	
}



