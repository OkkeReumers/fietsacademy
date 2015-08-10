package be.vdab.dao;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;
import be.vdab.filters.JPAFilter;

public class DocentDAO {
	public Docent read(long id) {
		// vraag een EntityManager aan de servlet filter JPAFilter
		EntityManager entityManager = JPAFilter.getEntityManager();
		try {
			// zoek een Docent entity op de primary key met de find method, eerste parameter is type te zoeken entity
			// 2e parameter is de primary key waarde van de op te zoeken entity
			return entityManager.find(Docent.class, id);
		} finally {
			entityManager.close();
		}
	}

}
