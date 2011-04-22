package org.sfnelson.sk.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity(name="characters")
@NamedQueries({
	@NamedQuery(name="allCharacters", query="select c from org.sfnelson.sk.server.domain.Character c"),
	@NamedQuery(name="countCharacters", query="select count(c) from org.sfnelson.sk.server.domain.Character c")
})
public class Character {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private String name;

	@Embedded
	private Realm realm;

	@Embedded
	private ArmoryReference armory;

	private Set<Long> groupIds;

	public Character() {
		this.groupIds = new HashSet<Long>();
	}

	public Character(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Realm getRealm() {
		return realm;
	}

	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	public ArmoryReference getArmory() {
		return armory;
	}

	public void setArmory(ArmoryReference armory) {
		this.armory = armory;
	}

	public Long getSeed() {
		return armory.getArmoryReference();
	}

	public Set<Long> getGroupIds() {
		return groupIds;
	}

	public void addGroup(Long groupId) {
		groupIds.add(groupId);
	}

	@Override
	public String toString() {
		return name;
	}
}
