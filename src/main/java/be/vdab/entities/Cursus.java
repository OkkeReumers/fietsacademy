package be.vdab.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

// enkele imports ...
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// Tik inheritance bij de hoogste class in de inheritance hiërarchie
// tik bij strategy op welke manier je inheritance nabootst in de database
// bij table per class hiërarchy wordt SINGLE_TABLE gebruikt
// bij table per class hierarchy gebruik je JOINED
// bij table per concrete class gebruik je TABLE_PER_CLASS
public abstract class Cursus implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private long id;
	private String naam;

	@Override
	public String toString() {
		return naam;
	}
}