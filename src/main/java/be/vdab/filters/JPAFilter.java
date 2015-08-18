package be.vdab.filters;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

// enkele imports ...
@WebFilter("*.htm")
public class JPAFilter implements Filter {
	private static final EntityManagerFactory entityManagerFactory = Persistence
			.createEntityManagerFactory("fietsacademy");

	// aanmaken van een ThreadLocal object, object blijft in het geheugen
	// gedurende de levensduur van de website --> static
	private static final ThreadLocal<EntityManager> entityManagers = new ThreadLocal<>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	// request verwerken
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		// maak een entitymanager en plaats die in de entitymanagers
		entityManagers.set(entityManagerFactory.createEntityManager());
		try {
			request.setCharacterEncoding("UTF-8");

			// geef de request door aan de servlet waarvoor hij bestemd is
			chain.doFilter(request, response);
		} finally {

			// nadat de servlet + jsp de request verwerkt heeft komt de
			// uitvoering van de kode hier terecht
			// je leest de entitymanager van de houdige thread
			EntityManager entityManager = entityManagers.get();
			if (entityManager.getTransaction().isActive()) {

				// als er nog een transactie actief is, doe je een rollback van
				// de transactie. Dit gebeurt als de
				// service layer geen commit deed op de transactie, omdat een
				// exception optrad
				entityManager.getTransaction().rollback();
			}

			// sluit de entitymanager
			entityManager.close();

			// verwijder de entitymanager van de huidige thread uit
			// entitymanagers
			entityManagers.remove();
		}
	}

	public static EntityManager getEntityManager() {

		// haal de entitymanager vanaf nu op uit de threadlocal variabele
		return entityManagers.get();
	}

	@Override
	public void destroy() {
		entityManagerFactory.close();
	}
}