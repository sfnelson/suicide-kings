package org.sfnelson.sk.client.place;

import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.place.shared.Place;

public class Group extends Place {

	private final Realm realm;
	private final String name;

	private Group(Realm realm, String name) {
		this.realm = realm;
		this.name = name;
	}

	public Realm getRealm() {
		return realm;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Group other = (Group) obj;
		return name.equals(other.name);
	}

	@Override
	public String toString() {
		return realm + "/" + name;
	}

	public static Group create(Realm realm, GroupProxy group) {
		return new Group(realm, group.getName().toLowerCase());
	}

	public static Group parse(Realm realm, String name) {
		return new Group(realm, name.toLowerCase());
	}

}
