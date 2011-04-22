package org.sfnelson.sk.client.view;

import java.util.List;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface NavigationView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setTrail(List<String> text, List<Place> targets);

	interface Presenter {
		void select(Place target);
	}
}
