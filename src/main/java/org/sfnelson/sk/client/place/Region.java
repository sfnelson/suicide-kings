package org.sfnelson.sk.client.place;

import com.google.gwt.place.shared.Place;

public class Region extends Place {

	public static final Region US = new Region("us");
	public static final Region EU = new Region("eu");

	private final String region;

	private Region(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		Region r = (Region) o;
		return region.equals(r.region);
	}

	@Override
	public int hashCode() {
		return region.hashCode();
	}

	@Override
	public String toString() {
		return region;
	}

	public static Region parse(String region) {
		if (region.equals("us")) {
			return US;
		}
		if (region.equals("eu")) {
			return EU;
		}
		return new Region(region.toLowerCase());
	}
}
