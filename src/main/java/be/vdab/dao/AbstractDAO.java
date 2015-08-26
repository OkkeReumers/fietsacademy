package be.vdab.dao;

import javax.persistence.EntityManager;

import be.vdab.filters.JPAFilter;

// enkele imports ...
abstract class AbstractDAO {

	// je kan deze protected method aanspreken vanuit DAO classes
	protected EntityManager getEntityManager() {
		// je leest de entitymanager van de huidige thread uit de threadlocal
		// variabele van jpafilter
		return JPAFilter.getEntityManager();
	}

	public void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public void commit() {
		getEntityManager().getTransaction().commit();
	}

	public void rollback() {
		getEntityManager().getTransaction().rollback();
	}
}test