package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.view.NavigationView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShowNavigation extends AbstractActivity implements NavigationView.Presenter {

	private final PlaceController pc;
	private final NavigationView view;

	private final List<String> texts = new ArrayList<String>();
	private final List<Place> targets = new ArrayList<Place>();

	public ShowNavigation(Factory factory, Place place) {
		pc = factory.getPlaceController();
		view = factory.getNavigationView();

		if (place instanceof org.sfnelson.sk.client.place.SelectGroup) {
			texts.add("Select Group");
			targets.add(place);
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		view.setTrail(texts, targets);
		panel.setWidget(view);
	}

	@Override
	public void select(Place target) {
		pc.goTo(target);
	}

}
