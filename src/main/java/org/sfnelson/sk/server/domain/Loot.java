package org.sfnelson.sk.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Loot {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @Version
    private Integer version;

    private Long reference;
    private String name;

    public Loot() {}

	public Loot(Long reference, String name) {
		this.reference = reference;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
	    return version;
	}

	public Long getReference() {
	    return reference;
	}

	public void setReference(Long reference) {
	    this.reference = reference;
	}

	public String getName() {
	    return name;
	}

	public String setName() {
	    return name;
	}

	@Override
	public String toString() {
		return "item[" + getReference() + "]";
	}
}
