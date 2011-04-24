package org.sfnelson.sk.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Event;
import org.sfnelson.sk.server.domain.Group;
import org.sfnelson.sk.server.domain.Realm;
import org.sfnelson.sk.server.request.CharacterService;
import org.sfnelson.sk.shared.EventType;

import com.google.gwt.requestfactory.shared.Locator;

public class CharacterManager extends Locator<Character, Long> implements CharacterService {

	private final RealmManager realms = new RealmManager();
	private final GroupManager groups = new GroupManager();

	@Override
	public Character registerCharacter(String region, String server, String groupName, String name) {
		Realm realm = realms.findRealm(region, server);
		Group group = groups.findGroup(realm, groupName);

		System.out.println("[svr] register character: " + name);
		ArmoryService am = ArmoryService.getInstance();

		Character character;
		try {
			character = am.getCharacter(realm, name);
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}

		System.out.println("[svr] " + character);

		if (character == null) {
			return null;
		}

		EntityManager em = EMF.get();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		em.persist(character);
		tx.commit();

		Event created = new Event(EventType.ADDED, group, character, "");

		tx.begin();
		em.persist(created);
		tx.commit();

		System.out.println("registered character: " + character);
		return character;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Character> findCharactersForGroup(String region, String server, String groupName) {
		Realm realm = realms.findRealm(region, server);
		Group group = groups.findGroup(realm, groupName);

		EntityManager em = EMF.get();

		Query q = em.createQuery("select from org.sfnelson.sk.server.domain.Event where type = :type and groupId = :group");
		q.setParameter("type", EventType.ADDED);
		q.setParameter("group", group.getId());
		List<Event> joins = q.getResultList();

		List<Character> characters = new ArrayList<Character>();
		for (Event e: joins) {
			characters.add(em.find(Character.class, e.getCharacterId()));
		}
		return characters;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Character> findCharacters(String region, String server) {
		Realm realm = realms.findRealm(region, server);

		EntityManager em = EMF.get();

		Query q = em.createNamedQuery("allCharactersInRealm");
		q.setParameter("realm", realm.getId());
		List<Character> characters = q.getResultList();
		characters.size();
		return characters;
	}

	@Override
	public Character findCharacter(Long id) {
		return EMF.get().find(Character.class, id);
	}

	@Override
	public Character create(Class<? extends Character> clazz) {
		return new Character();
	}

	@Override
	public Character find(Class<? extends Character> clazz, Long id) {
		return EMF.get().find(Character.class, id);
	}

	@Override
	public Class<Character> getDomainType() {
		return Character.class;
	}

	@Override
	public Long getId(Character character) {
		return character.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Integer getVersion(Character character) {
		return character.getVersion();
	}

}
