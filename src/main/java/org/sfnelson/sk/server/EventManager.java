package org.sfnelson.sk.server;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Event;
import org.sfnelson.sk.server.domain.Group;
import org.sfnelson.sk.server.domain.Loot;
import org.sfnelson.sk.server.request.EventService;
import org.sfnelson.sk.shared.EventType;

import com.google.gwt.requestfactory.shared.Locator;

public class EventManager extends Locator<Event, Long> implements EventService {

	private final GroupManager groups = new GroupManager();
	private final CharacterManager characters = new CharacterManager();
	private final LootManager loots = new LootManager();

	@Override
	public void assign(String region, String server, String groupName,
			Long characterId, Long reference) {
		Group group = groups.findGroup(region, server, groupName);
		Character character = characters.findCharacter(characterId);
		Loot loot = loots.findLootByReference(reference);

		EntityManager em = EMF.get();
		EntityTransaction tx = em.getTransaction();

		Event event = new Event(EventType.LOOT, group, character, loot, "suicide");
		tx.begin();
		em.persist(event);
		tx.commit();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Event> getEvents(Group group, Date after) {
		if (after == null) {
			after = new Date(0);
		}

		Query q = EMF.get().createQuery("select from " + Event.class.getName() + " where groupId = :group and date > :date");
		q.setParameter("group", group.getId());
		q.setParameter("date", after);
		List<Event> result = q.getResultList();
		result.size(); // load lazy collection
		return result;
	}

	@Override
	public void joinParty(Group group, Character character) {
		Event event = new Event(EventType.JOINED, group, character, "");

		EMF.get().persist(event);
	}

	@Override
	public void leaveParty(Group group, Character character) {
		Event event = new Event(EventType.LEFT, group, character, "");

		EMF.get().persist(event);
	}

	@Override
	public Event create(Class<? extends Event> clazz) {
		return new Event();
	}

	@Override
	public Event find(Class<? extends Event> clazz, Long id) {
		if (id == null) {
			return null;
		}

		return EMF.get().find(clazz, id);
	}

	@Override
	public Class<Event> getDomainType() {
		return Event.class;
	}

	@Override
	public Long getId(Event event) {
		return event.getId();
	}

	@Override
	public Class<Long> getIdType() {
		return Long.class;
	}

	@Override
	public Integer getVersion(Event event) {
		return event.getVersion();
	}

}
