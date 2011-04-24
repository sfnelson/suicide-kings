package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.event.SuicideKingsEvent;

import com.google.gwt.user.client.ui.IsWidget;

public interface EventsView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setEvents(List<SuicideKingsEvent> response);

	public interface Presenter {

	}
}
