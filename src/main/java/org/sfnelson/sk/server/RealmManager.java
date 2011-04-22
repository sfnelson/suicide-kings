package org.sfnelson.sk.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Realm;
import org.sfnelson.sk.server.request.RealmService;

import com.google.gwt.requestfactory.shared.Locator;

public class RealmManager extends Locator<Realm, Long> implements RealmService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Realm> getRealms(String region) {
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createNamedQuery("realmsInRegion");
			q.setParameter("region", region);
			List<Realm> result = q.getResultList();
			result.size(); // force load lazy collection.
			return result;
		}
		finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(String region) {
		List<Realm> update = ArmoryService.getInstance().getRealms(region);

		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			Map<String, Realm> current = new HashMap<String, Realm>();
			Query q = em.createNamedQuery("realmsInRegion");
			q.setParameter("region", region);
			for (Realm realm: ((List<Realm>) q.getResultList())) {
				current.put(realm.getServer(), realm);
			}

			for (Realm realm: update) {
				if (!current.containsKey(realm.getServer())) {
					tx.begin();
					em.persist(realm);
					tx.commit();
				}
				else {
					Realm old = current.get(realm.getServer());
					old.setType(realm.getType());
					old.setPopulation(realm.getPopulation());
					tx.begin();
					old.setLocale(realm.getLocale());
					em.merge(old);
					tx.commit();
				}
			}
		}
		catch (RuntimeException ex) {
			if (tx.isActive()) {
				tx.rollback();
			}
			ex.printStackTrace();
		}
		finally {
			em.close();
		}
	}

	@Override
	public Realm create(Class<? extends Realm> clazz) {
		return new Realm();
	}

	@Override
	public Realm find(Class<? extends Realm> clazz, Long id) {
		if (id == null) {
			return null;
		}

		EntityManager em = EMF.get().createEntityManager();
		try {
			return em.find(Realm.class, id);
		}
		finally {
			em.close();
		}
	}

	@Override
	public Class<Realm> getDomainType() {
		return Realm.class;
	}

	@Override
	public Long getId(Realm realm) {
		return realm.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.TYPE;
	}

	@Override
	public Integer getVersion(Realm realm) {
		return realm.getVersion();
	}

}
