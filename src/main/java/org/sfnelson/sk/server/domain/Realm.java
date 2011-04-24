package org.sfnelson.sk.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity
@NamedQueries({
	@NamedQuery(name="allRealms", query="select r from org.sfnelson.sk.server.domain.Realm r"),
	@NamedQuery(name="countRealms", query="select count(r) from org.sfnelson.sk.server.domain.Realm r"),
	@NamedQuery(name="realmsInRegion", query="select r from org.sfnelson.sk.server.domain.Realm r where r.region = :region"),
	@NamedQuery(name="numRealmsInRegion", query="select count(r) from org.sfnelson.sk.server.domain.Realm r where r.region = :region"),
})
public class Realm {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Version
	private Integer version;

	private String region;
	private String server;
	private String type;
	private String population;
	private String locale;

	@SuppressWarnings("unused")
	private String lserver;

	public Realm() {}

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
		this.lserver = server.toLowerCase();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPopulation() {
		return population;
	}

	public void setPopulation(String population) {
		this.population = population;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Realm other = (Realm) obj;
		return id.equals(other.id);
	}

	@Override
	public String toString() {
		return region + "-" + server;
	}
}
