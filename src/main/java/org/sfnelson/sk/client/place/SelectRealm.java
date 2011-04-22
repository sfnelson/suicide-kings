package org.sfnelson.sk.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SelectRealm extends Place {

	private final String region;

	public SelectRealm(String region) {
		this.region = region;
	}

	public String getRegion() {
		return region;
	}

	public static class Tokenizer implements PlaceTokenizer<SelectRealm> {

		@Override
		public SelectRealm getPlace(String token) {
			return new SelectRealm(token);
		}

		@Override
		public String getToken(SelectRealm place) {
			return place.getRegion();
		}

	}
}
