package be.vdab.services;

import javax.persistence.EntityManager;

import be.vdab.dao.DocentDAO;
import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;

public class DocentService {
	private final DocentDAO docentDAO = new DocentDAO();

	// DocentService gebruikt DocentDA

	public Docent read(long id) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		// EntityManager aan de servlet filter JPAFilter vragen
		try {
			return docentDAO.read(id, entityManager);
			// roep de DAO layer op en geef de EntityManager mee
		} finally {
			entityManager.close();
			// sluit de entity manager
		}
	}

	public void create(Docent docent) {
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			docentDAO.create(docent, entityManager);
			entityManager.getTransaction().commit();
		} catch (RuntimeException ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally {
			entityManager.close();
		}
	}

}
