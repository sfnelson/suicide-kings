package org.sfnelson.sk.client;

import org.sfnelson.sk.client.activity.ShowNavigation;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class NavigationActivityMapper implements ActivityMapper {

	private final Factory factory;

	public NavigationActivityMapper(Factory factory) {
		this.factory = factory;
	}

	@Override
	public Activity getActivity(Place place) {
		return new ShowNavigation(factory, place);
	}

}
