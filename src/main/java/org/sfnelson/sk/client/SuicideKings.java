package org.sfnelson.sk.client;

import org.sfnelson.sk.client.event.NotAuthenticatedEvent;
import org.sfnelson.sk.client.event.NotAuthenticatedEvent.AuthenticationHandler;
import org.sfnelson.sk.client.place.Region;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SuicideKings implements EntryPoint, AuthenticationHandler {

	@Override
	public void onModuleLoad() {

		Factory factory = GWT.create(Factory.class);
		EventBus eventBus = factory.getEventBus();
		PlaceController placeController = factory.getPlaceController();

		HistoryMapper historyMapper = new HistoryMapper();
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, Region.US);

		NotAuthenticatedEvent.register(eventBus, this);

		new SuicideKingsApplication(factory).start(new AcceptsOneWidget() {
			@Override
			public void setWidget(IsWidget child) {
				RootPanel.get().add(child);
			}
		}, eventBus);

		historyHandler.handleCurrentHistory();
	}

	@Override
	public void onAuthenticationRequired(String loginURL) {
		Window.Location.replace(loginURL);
	}
}
