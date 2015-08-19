package be.vdab.services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

import be.vdab.dao.CampusDAO;
import be.vdab.dao.DocentDAO;
import be.vdab.entities.Docent;
import be.vdab.exceptions.DocentBestaatAlException;
import be.vdab.exceptions.RecordAangepastException;
import be.vdab.valueobjects.AantalDocentenPerWedde;
import be.vdab.valueobjects.VoornaamEnId;

public class DocentService {
	private final DocentDAO docentDAO = new DocentDAO();

	// DocentService gebruikt DocentDAO

	// DOCENT OPZOEKEN
	public Docent read(long id) {
		return docentDAO.read(id);
	}

	// NIEUWE DOCENT TOEVOEGEN //
	public void create(Docent docent) {
		if (docentDAO.findByRijksRegisterNr(docent.getRijksRegisterNr()) != null) {
			throw new DocentBestaatAlException();
		}
		docentDAO.beginTransaction();
		docentDAO.create(docent);
		docentDAO.commit();
	}

	// DOCENT VERWIJDEREN //
	public void delete(long id) {
		docentDAO.beginTransaction();
		docentDAO.delete(id);
		docentDAO.commit();
	}

	// DOCENT OPSLAG GEVEN //
	public void opslag(long id, BigDecimal percentage) {
		docentDAO.beginTransaction();
		docentDAO.read(id).opslag(percentage);
		try {
			docentDAO.commit();
		} catch (RollbackException ex) {
			if (ex.getCause() instanceof OptimisticLockException) {
				throw new RecordAangepastException();
			}
		}
	}

	// WEDDE TUSSEN 2 WAARDES VINDEN
	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot,
			int vanafRij, int aantalRijen) {
		return docentDAO.findByWeddeBetween(van, tot, vanafRij, aantalRijen);
	}

	// VIND ALLE VOORNAMEN MET HUN ID
	public List<VoornaamEnId> findVoornamen() {
		return docentDAO.findVoornamen();
	}

	// VIND DE MAXIMALE WEDDE
	public BigDecimal findMaxWedde() {
		return docentDAO.findMaxWedde();
	}

	// VIND AANTAL DOCENTEN PER WEDDE
	public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
		return docentDAO.findAantalDocentenPerWedde();
	}

	// GEEF DOCENTEN EEN ALGEMENE OPSLAG
	public void algemeneOpslag(BigDecimal percentage) {
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal
				.valueOf(100)));
		docentDAO.beginTransaction();
		docentDAO.algemeneOpslag(factor);
		docentDAO.commit();
	}

	// BIJNAAM TOEVOEGEN
	public void bijnaamToevoegen(long id, String bijnaam) {
		docentDAO.beginTransaction();
		docentDAO.read(id).addBijnaam(bijnaam);
		docentDAO.commit();
	}

	// BIJNAAM VERWIJDEREN
	public void bijnamenVerwijderen(long id, String[] bijnamen) {
		docentDAO.beginTransaction();
		Docent docent = docentDAO.read(id);
		for (String bijnaam : bijnamen) {
			docent.removeBijnaam(bijnaam);
		}
		docentDAO.commit();
	}

	private final CampusDAO campusDAO = new CampusDAO();

	public List<Docent> findBestBetaaldeVanEenCampus(long id) {
		return docentDAO.findBestBetaaldeVanEenCampus(campusDAO.read(id));
	}
}
