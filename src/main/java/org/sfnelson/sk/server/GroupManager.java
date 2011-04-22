package org.sfnelson.sk.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Group;
import org.sfnelson.sk.server.domain.Realm;
import org.sfnelson.sk.server.request.GroupService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.requestfactory.shared.Locator;

public class GroupManager extends Locator<Group, Long> implements GroupService {

	@SuppressWarnings("unchecked")
	public List<Group> findGroups() {
		EntityManager em = EMF.get().createEntityManager();
		try {
			List<Group> groups = em.createNamedQuery("allGroups").getResultList();
			groups.size();
			return groups;
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Group findGroup(String name, Realm realm) {
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select from " + Group.class.getName() + " where name = :name");
			q.setParameter("name", name);
			// would like to query for realm and server too, but datanucleus has a bug.
			List<Group> groups = q.getResultList();
			List<Group> matches = new ArrayList<Group>();
			for (Group g: groups) {
				if (g.getRealm().equals(realm)) {
					matches.add(g);
				}
			}

			if (matches.isEmpty()) return null;
			else return matches.get(0);
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			em.close();
		}
	}

	public void createGroup(String name, Realm realm) {
		if (name == null || realm == null || realm.getRegion() == null || realm.getServer() == null) {
			throw new RuntimeException("invalid group parameters");
		}

		Group group = findGroup(name, realm);

		if (group != null) {
			throw new RuntimeException("group already exists");
		}

		User user = UserServiceFactory.getUserService().getCurrentUser();
		group = new Group();
		group.setName(name);
		group.setRealm(realm);
		group.addOwner(user.getUserId());

		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction txn = em.getTransaction();
		try {
			txn.begin();
			em.persist(group);
			txn.commit();
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			em.close();
		}
	}

	public void deleteGroup(Group group) {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		System.out.println("delete " + group.getName());

		EntityManager em = EMF.get().createEntityManager();
		group = em.find(Group.class, group.getId());

		if (!group.getOwners().contains(user.getUserId())) {
			throw new RuntimeException("Not allowed to delete this group");
		}

		EntityTransaction txn = em.getTransaction();
		try {
			txn.begin();
			em.remove(group);
			txn.commit();
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			em.close();
		}
	}

	@Override
	public Group create(Class<? extends Group> clazz) {
		return new Group();
	}

	@Override
	public Group find(Class<? extends Group> clazz, Long id) {
		if (id == null) {
			return null;
		}

		EntityManager em = EMF.get().createEntityManager();
		try {
			return em.find(clazz, id);
		}
		finally {
			em.close();
		}
	}

	@Override
	public Class<Group> getDomainType() {
		return Group.class;
	}

	@Override
	public Long getId(Group group) {
		return group.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Integer getVersion(Group group) {
		return group.getVersion();
	}
}
