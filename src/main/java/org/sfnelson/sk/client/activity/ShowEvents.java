package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.view.EventsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShowEvents extends AbstractActivity implements EventsView.Presenter, SuicideKingsEvent.EventHandler {

	private final EventsView view;
	private final Group group;

	private final List<SuicideKingsEvent> events = new ArrayList<SuicideKingsEvent>();

	public ShowEvents(Factory factory, Group group) {
		this.view = factory.getEventsView();
		this.group = group;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		panel.setWidget(view);

		SuicideKingsEvent.register(eventBus, this);
	}

	private void addEvent(SuicideKingsEvent event) {
		events.add(event);

		view.setEvents(events);
	}

	@Override
	public void onCharacterAdded(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void onCharacterJoined(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void onCharacterLeft(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void onReceivedLoot(SuicideKingsEvent event) {
		addEvent(event);
	}
}
