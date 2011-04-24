package org.sfnelson.sk.client;

import java.util.ArrayList;
import java.util.List;

import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.place.Region;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;

public class HistoryMapper implements PlaceHistoryMapper {

	@Override
	public Place getPlace(String token) {
		List<String> tokens = tokenize(token);

		if (tokens.isEmpty()) return Region.US;

		Region region = Region.parse(tokens.get(0));

		if (tokens.size() == 1) return region;

		Realm realm = Realm.parse(region, tokens.get(1));

		if (tokens.size() == 2) return realm;

		Group group = Group.parse(realm, tokens.get(2));

		if (tokens.size() == 3) return group;

		throw new RuntimeException("unable to parse " + token);
	}

	@Override
	public String getToken(Place place) {
		return place.toString();
	}

	private List<String> tokenize(String token) {
		List<String> tokens = new ArrayList<String>();

		int i = token.indexOf('/');
		while (i >= 0) {
			String tok = token.substring(0, i);
			if (tok.length() > 0) {
				tokens.add(tok);
			}
			token = token.substring(i + 1);
			i = token.indexOf('/');
		}
		if (token.length() > 0) {
			tokens.add(token);
		}

		return tokens;
	}
}
