package be.vdab.valueobjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
// moet voor een value object class
public class Adres implements Serializable {
	private static final long serialVersionUID = 1L;
	private String straat;
	private String huisNr;
	private String postcode;
	private String gemeente;

	// je maakt voor straat, huisNr, postcode en gemeente getters, geen setters

	public Adres(String straat, String huisNr, String postcode, String gemeente) {
		this.straat = straat;
		this.huisNr = huisNr;
		this.postcode = postcode;
		this.gemeente = gemeente;
	}

	// constructor bevat per private variabele een parameter

	protected Adres() {

	}

	// een value object class moet bij JPA een default constructor hebben

	public String getStraat() {
		return straat;
	}

	public String getHuisNr() {
		return huisNr;
	}

	public String getPostcode() {
		return postcode;
	}

	public String getGemeente() {
		return gemeente;
	}

}
