package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
// enkele imports (vooral uit javax.persistence)...




import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.enums.Geslacht;

@Entity
@Table(name = "docenten")
public class Docent implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private long rijksRegisterNr;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getFamilienaam() {
		return familienaam;
	}

	public void setFamilienaam(String familienaam) {
		this.familienaam = familienaam;
	}

	public BigDecimal getWedde() {
		return wedde;
	}

	public void setWedde(BigDecimal wedde) {
		this.wedde = wedde;
	}

	public long getRijksRegisterNr() {
		return rijksRegisterNr;
	}

	public void setRijksRegisterNr(long rijksRegisterNr) {
		this.rijksRegisterNr = rijksRegisterNr;
	}

	public Geslacht getGeslacht() {
		return geslacht;
	}

	public String getNaam() {
		return voornaam + ' ' + familienaam;
	}

}
