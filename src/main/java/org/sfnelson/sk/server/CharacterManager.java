package org.sfnelson.sk.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Group;
import org.sfnelson.sk.server.request.CharacterService;

import com.google.gwt.requestfactory.shared.Locator;

public class CharacterManager extends Locator<Character, Long> implements CharacterService {

	@Override
	public Character registerCharacter(Group group, String name) {
		System.out.println("[svr] register character: " + name);
		ArmoryService am = ArmoryService.getInstance();

		Character character;
		try {
			character = am.getCharacter(group.getRealm(), name);
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}

		System.out.println("[svr] " + character);

		if (character == null) {
			return null;
		}

		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx1 = em.getTransaction();
		EntityTransaction tx2 = em.getTransaction();
		try {
			tx1.begin();
			em.persist(character);
			tx1.commit();

			em.refresh(character);
			em.refresh(group);

			tx2.begin();
			character.addGroup(group.getId());
			group.addCharacter(character.getId());
			tx2.commit();
		}
		catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			em.close();
		}

		System.out.println("registered character: " + character);
		return character;
	}

	@Override
	public List<Character> findCharactersForGroup(Group group) {
		List<Character> characters = new ArrayList<Character>();
		for (Character c: findCharacters()) {
			if (c.getGroupIds().contains(group.getId())) {
				characters.add(c);
			}
		}
		return characters;
	}

	@SuppressWarnings("unchecked")
	public List<Character> findCharacters() {
		EntityManager em = EMF.get().createEntityManager();
		try {
			List<Character> characters = em.createNamedQuery("allCharacters").getResultList();
			characters.size();
			return characters;
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
	public Character create(Class<? extends Character> clazz) {
		return new Character();
	}

	@Override
	public Character find(Class<? extends Character> clazz, Long id) {
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
