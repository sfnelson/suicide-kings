package org.sfnelson.sk.server.domain;

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
	@NamedQuery(name="allCharactersInRealm", query="select c from org.sfnelson.sk.server.domain.Character c where c.realmId = :realm"),
	@NamedQuery(name="countCharactersInRealm", query="select count(c) from org.sfnelson.sk.server.domain.Character c where c.realmId = :realm")
})
public class Character {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private String name;

	private Long realmId;

	@Embedded
	private ArmoryReference armory;

	public Character() {}

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

	public Long getRealm() {
		return realmId;
	}

	public void setRealm(Long realmId) {
		this.realmId = realmId;
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

	@Override
	public String toString() {
		return name;
	}
}
