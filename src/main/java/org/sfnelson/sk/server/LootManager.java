package org.sfnelson.sk.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Loot;
import org.sfnelson.sk.server.request.LootService;

import com.google.gwt.requestfactory.shared.Locator;

public class LootManager extends Locator<Loot, Long> implements LootService {

	@Override
	public Loot findLootById(Long id) {
		return find(Loot.class, id);
	}

	@Override
	public List<Loot> findLootsById(List<Long> ids) {
		List<Loot> response = new ArrayList<Loot>();
		for (Long id: ids) {
			response.add(findLootById(id));
		}
		return response;
	}

	@Override
	public Loot findLootByReference(Long reference) {
		EntityManager em = EMF.get();
		EntityTransaction tx = em.getTransaction();

		Loot loot;
		try {
			Query q = em.createQuery("select l from org.sfnelson.sk.server.domain.Loot l where l.reference = :reference");
			q.setParameter("reference", reference);
			loot = (Loot) q.getSingleResult();
		}
		catch (NoResultException ex) {
			loot = null;
		}

		if (loot != null) {
			return loot;
		}

		loot = WowheadService.getInstance().getLoot(reference);

		if (loot == null) {
			loot = new Loot(reference, "unknown");
		}

		tx.begin();
		em.persist(loot);
		tx.commit();

		System.out.println("added " + loot);

		return loot;
	}

	@Override
	public Loot create(Class<? extends Loot> clazz) {
		return new Loot();
	}

	@Override
	public Loot find(Class<? extends Loot> clazz, Long id) {
		if (id == null) {
			return null;
		}

		return EMF.get().find(clazz, id);
	}

	@Override
	public Class<Loot> getDomainType() {
		return Loot.class;
	}

	@Override
	public Long getId(Loot loot) {
		return loot.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Integer getVersion(Loot loot) {
		return loot.getVersion();
	}
}
