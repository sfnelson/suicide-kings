package org.sfnelson.sk.server.request;

import java.util.Date;
import java.util.List;

import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Event;
import org.sfnelson.sk.server.domain.Group;

public interface EventService {

	void assign(String region, String server, String group, Long characterId, Long lootId);
	void joinParty(Group group, Character character);
	void leaveParty(Group group, Character character);
	List<Event> getEvents(Group group, Date lastUpdate);

}
