package org.sfnelson.sk.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.sfnelson.sk.shared.EventType;

@Entity(name="events")
public class Event {

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

	@Version
	private Integer version;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private EventType type;

	private Long groupId;

	private Long characterId;

	private Long lootId;

	private String info;

	public Event() {}

	public Event(EventType type, Group group, Character character, Loot loot, String info) {
		this.type = type;
		this.groupId = group.getId();
		this.characterId = character.getId();
		this.lootId = loot.getId();
		this.info = info;

		this.date = new Date();
	}

	public Long getId() {
	    return id;
	}

	public Integer getVersion() {
	    return version;
	}

	public Date getDate() {
	    return date;
	}

	public void setDate(Date date) {
	    this.date = date;
	}

	public EventType getType() {
	    return type;
	}

	public void setType(EventType type) {
	    this.type = type;
	}

	public Long getGroupId() {
	    return groupId;
	}

	public void setGroupId(Long groupId) {
	    this.groupId = groupId;
	}

	public Long getCharacterId() {
	    return characterId;
	}

	public void setCharacterId(Long characterId) {
	    this.characterId = characterId;
	}

	public Long getLootId() {
	    return lootId;
	}

	public void setLootId(Long lootId) {
	    this.lootId = lootId;
	}

	public String getInfo() {
	    return info;
	}

	public void setInfo(String info) {
	    this.info = info;
	}
}
