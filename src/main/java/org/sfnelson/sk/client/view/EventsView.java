package org.sfnelson.sk.client.view;

import org.sfnelson.sk.client.event.SuicideKingsEvent;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;

public interface EventsView extends IsWidget {

	void setPresenter(Presenter presenter);
	void addEvent(SuicideKingsEvent event);

	public interface Presenter {

		void assign(String character, String loot);
		void requestSuggestion(Request request, Callback callback);

	}
}
