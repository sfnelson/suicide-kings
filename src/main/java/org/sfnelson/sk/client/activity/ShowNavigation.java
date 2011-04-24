package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.place.Region;
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

		if (place instanceof Region) {
			addRegion((Region) place);
			texts.add("Select Realm");
			targets.add(place);
		}
		else if (place instanceof Realm) {
			addRealm((Realm) place);
			texts.add("Select Group");
			targets.add(place);
		}
		else if (place instanceof Group) {
			addGroup((Group) place);
		}
	}

	private void addRegion(Region region) {
		texts.add(region.getRegion().toUpperCase());
		targets.add(region);
	}

	private void addRealm(Realm realm) {
		addRegion(realm.getRegion());
		texts.add(realm.getServer());
		targets.add(realm);
	}

	private void addGroup(Group group) {
		addRealm(group.getRealm());
		texts.add(group.getName());
		targets.add(group);
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
