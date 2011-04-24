package org.sfnelson.sk.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Realm;
import org.sfnelson.sk.server.request.RealmService;

import com.google.gwt.requestfactory.shared.Locator;

public class RealmManager extends Locator<Realm, Long> implements RealmService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Realm> getRealms(String region) {
		Query q = EMF.get().createNamedQuery("realmsInRegion");
		q.setParameter("region", region);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(String region) {
		List<Realm> update = ArmoryService.getInstance().getRealms(region);

		EntityManager em = EMF.get();
		EntityTransaction tx = em.getTransaction();

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
				tx.begin();
				Realm old = current.get(realm.getServer());
				old.setServer(realm.getServer());
				old.setType(realm.getType());
				old.setPopulation(realm.getPopulation());
				old.setLocale(realm.getLocale());
				em.merge(old);
				tx.commit();
			}
		}
	}

	public Realm findRealm(String region, String server) {
		try {
			Query q = EMF.get().createQuery("select r from org.sfnelson.sk.server.domain.Realm r"
					+ " where r.region = :region and r.lserver = :server");
			q.setParameter("region", region);
			q.setParameter("server", server.toLowerCase());
			return (Realm) q.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
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

		return EMF.get().find(Realm.class, id);
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
