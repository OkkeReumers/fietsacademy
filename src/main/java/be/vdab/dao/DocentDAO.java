package be.vdab.dao;

import javax.persistence.EntityManager;

import be.vdab.entities.Docent;


public class DocentDAO {
	
	public Docent read(long id, EntityManager entityManager) {
		// zoek een Docent entity op de primary key met de find method, eerste
		// parameter is type te zoeken entity
		// 2e parameter is de primary key waarde van de op te zoeken entity
		return entityManager.find(Docent.class, id);

	}
	
	public void create(Docent docent, EntityManager entityManager) {
		entityManager.persist(docent);
		}

}
