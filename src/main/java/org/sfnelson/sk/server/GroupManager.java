package org.sfnelson.sk.server;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Admin;
import org.sfnelson.sk.server.domain.Group;
import org.sfnelson.sk.server.domain.Realm;
import org.sfnelson.sk.server.request.GroupService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.requestfactory.shared.Locator;

public class GroupManager extends Locator<Group, Long> implements GroupService {

	private final RealmManager realms = new RealmManager();

	@Override
	@SuppressWarnings("unchecked")
	public List<Group> findGroups(String region, String server) {
		Realm realm = realms.findRealm(region, server);

		if (realm == null) return null;

		Query q = EMF.get().createNamedQuery("groupsForRealm");
		q.setParameter("realm", realm.getId());
		return q.getResultList();
	}

	@Override
	public Group findGroup(String region, String server, String name) {
		Realm realm = realms.findRealm(region, server);

		if (realm == null) return null;

		return findGroup(realm, name);
	}

	public Group findGroup(Realm realm, String name) {
		try {
			Query q = EMF.get().createQuery("select from " + Group.class.getName() + " where lname = :name and realmId = :realmId");
			q.setParameter("name", name.toLowerCase());
			q.setParameter("realmId", realm.getId());
			return (Group) q.getSingleResult();
		}
		catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public Group createGroup(String region, String server, String name) {
		if (name == null || region == null || server == null) {
			throw new RuntimeException("invalid group parameters");
		}

		Realm realm = realms.findRealm(region, server);

		if (findGroup(realm, name) != null) {
			throw new RuntimeException("group already exists");
		}

		User user = UserServiceFactory.getUserService().getCurrentUser();
		EntityManager em = EMF.get();
		EntityTransaction txn = em.getTransaction();

		Group group = new Group();
		group.setName(name);
		group.setRealm(realm.getId());

		txn.begin();
		em.persist(group);
		txn.commit();

		Admin admin = new Admin();
		admin.setAccountId(user.getUserId());
		admin.setGroupId(group.getId());

		txn.begin();
		em.persist(admin);
		txn.commit();

		return group;
	}

	@Override
	public void deleteGroup(Group group) {
		User user = UserServiceFactory.getUserService().getCurrentUser();

		System.out.println("delete " + group.getName());

		EntityManager em = EMF.get();
		EntityTransaction tx = em.getTransaction();

		Query q = em.createQuery("select p from org.sfnelson.sk.server.domain.Admin p where p.accountId = :account and p.groupId = :group");
		q.setParameter("account", user.getUserId());
		q.setParameter("group", group.getId());
		Admin admin;
		try {
			admin = (Admin) q.getSingleResult();
		}
		catch (NoResultException ex) {
			throw new RuntimeException("Permission denied: not a group owner");
		}

		tx.begin();
		q = em.createQuery("delete from org.sfnelson.sk.server.domain.Event where groupId = :group");
		q.setParameter("group", group.getId());
		q.executeUpdate();
		em.remove(group);
		em.remove(admin);
		tx.commit();
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

		Group group = EMF.get().find(clazz, id);
		return group;
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
