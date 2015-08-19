package be.vdab.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

import be.vdab.entities.Campus;
import be.vdab.entities.Docent;
import be.vdab.valueobjects.AantalDocentenPerWedde;
import be.vdab.valueobjects.VoornaamEnId;

public class DocentDAO extends AbstractDAO {

	public Docent read(long id) {
		// zoek een Docent entity op de primary key met de find method, eerste
		// parameter is type te zoeken entity
		// 2e parameter is de primary key waarde van de op te zoeken entity
		return getEntityManager().find(Docent.class, id);

	}

	public void create(Docent docent) {
		getEntityManager().persist(docent);
	}

	public void delete(long id) {
		Docent docent = getEntityManager().find(Docent.class, id);
		if (docent != null) {
			getEntityManager().remove(docent);
		}
	}

	public List<Docent> findByWeddeBetween(BigDecimal van, BigDecimal tot,
			int vanafRij, int aantalRijen) {
		return getEntityManager()
				.createNamedQuery("Docent.findByWeddeBetween", Docent.class)
				.setParameter("van", van)
				.setParameter("tot", tot)
				.setFirstResult(vanafRij)
				.setMaxResults(aantalRijen)
				.setHint(
						"javax.persistence.loadgraph",
						getEntityManager()
								.createEntityGraph("Docent.metCampus"))
				.getResultList();
	}

	public List<VoornaamEnId> findVoornamen() {
		return getEntityManager()
				.createQuery(
						"select new be.vdab.valueobjects.VoornaamEnId(d.id, d.voornaam) from Docent d",
						VoornaamEnId.class).getResultList();
	}

	public BigDecimal findMaxWedde() {
		return getEntityManager().createQuery(
				"select max(d.wedde) from Docent d", BigDecimal.class)
				.getSingleResult();
	}

	public List<AantalDocentenPerWedde> findAantalDocentenPerWedde() {
		return getEntityManager().createQuery(
				"select new be.vdab.valueobjects.AantalDocentenPerWedde(d.wedde,count(d))"
						+ " from Docent d group by d.wedde",
				AantalDocentenPerWedde.class).getResultList();
	}

	public void algemeneOpslag(BigDecimal factor) {
		getEntityManager().createNamedQuery("Docent.algemeneOpslag")
				.setParameter("factor", factor).executeUpdate();
	}

	public Docent findByRijksRegisterNr(long rijksRegisterNr) {
		try {
			return getEntityManager()
					.createNamedQuery("Docent.findByRijksRegisterNr",
							Docent.class)
					.setParameter("rijksRegisterNr", rijksRegisterNr)
					.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	public List<Docent> findBestBetaaldeVanEenCampus(Campus campus) {
		return getEntityManager()
				.createNamedQuery("Docent.findBestBetaaldeVanEenCampus",
						Docent.class).setParameter("campus", campus)
				.getResultList();
	}

	public Docent readWithLock(long id) {
		return getEntityManager().find(Docent.class, id,
				LockModeType.PESSIMISTIC_WRITE);
	}
}
