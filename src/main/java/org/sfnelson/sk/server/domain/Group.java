package org.sfnelson.sk.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity(name="groups")
@NamedQueries({
	@NamedQuery(name="groupsForRealm", query="select g from org.sfnelson.sk.server.domain.Group g where realmId = :realm"),
})
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private Long realmId;

	private String name;

	@SuppressWarnings("unused")
	private String lname;

	public Group() {}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public Long getRealmId() {
		return realmId;
	}

	public void setRealm(Long realmId) {
		this.realmId = realmId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.lname = name.toLowerCase();
	}
}
