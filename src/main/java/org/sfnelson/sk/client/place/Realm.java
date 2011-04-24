package org.sfnelson.sk.client.place;

import com.google.gwt.place.shared.Place;

public class Realm extends Place {

	private final Region region;
	private final String server;

	private Realm(Region region, String server) {
		this.region = region;
		this.server = server;
	}

	public Region getRegion() {
		return region;
	}

	public String getServer() {
		return server;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((server == null) ? 0 : server.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Realm other = (Realm) obj;
		if (!region.equals(other.region)) return false;
		if (!server.equals(other.server)) return false;
		return true;
	}

	@Override
	public String toString() {
		return region + "/" + server;
	}

	public static Realm parse(String region, String server) {
		return new Realm(Region.parse(region), server.toLowerCase());
	}

	public static Realm parse(Region region, String server) {
		return new Realm(region, server.toLowerCase());
	}

}
