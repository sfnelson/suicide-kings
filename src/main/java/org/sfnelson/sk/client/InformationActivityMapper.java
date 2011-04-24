package org.sfnelson.sk.client;

import org.sfnelson.sk.client.activity.ShowEvents;
import org.sfnelson.sk.client.place.Group;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class InformationActivityMapper implements ActivityMapper {

	private final Factory factory;

	public InformationActivityMapper(Factory factory) {
		this.factory = factory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof Group) {
			return new ShowEvents(factory, (Group) place);
		}

		return null;
	}

}
