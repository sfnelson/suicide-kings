package org.sfnelson.sk.server;

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

public class EMF implements Filter {

	private static EntityManagerFactory factory;

	public EMF() {}

	private static ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();

	static EntityManager get() {
		return em.get();
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		factory = Persistence.createEntityManagerFactory("transactions-optional");
	}

	@Override
	public void destroy() {
		factory.close();
		factory = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
	throws IOException, ServletException {

		em.set(factory.createEntityManager());

		try {
			chain.doFilter(req, resp);
		}
		finally {
			em.get().close();
			em.set(null);
		}
	}
}
