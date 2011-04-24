package org.sfnelson.sk.client;

import org.sfnelson.sk.client.activity.SelectGroup;
import org.sfnelson.sk.client.activity.SelectRealm;
import org.sfnelson.sk.client.activity.ShowLadder;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.place.Region;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class SelectionActivityMapper implements com.google.gwt.activity.shared.ActivityMapper {

	private final Factory factory;

	public SelectionActivityMapper(Factory factory) {
		this.factory = factory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof Region) {
			return new SelectRealm(factory, (Region) place);
		}
		if (place instanceof Realm) {
			return new SelectGroup(factory, (Realm) place);
		}
		if (place instanceof Group) {
			return new ShowLadder(factory, (Group) place);
		}

		return null;
	}

}
