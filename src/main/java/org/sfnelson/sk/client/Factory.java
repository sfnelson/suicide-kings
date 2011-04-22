package org.sfnelson.sk.client;

import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.view.ApplicationView;
import org.sfnelson.sk.client.view.EventsView;
import org.sfnelson.sk.client.view.GroupDetailsView;
import org.sfnelson.sk.client.view.GroupView;
import org.sfnelson.sk.client.view.LadderView;
import org.sfnelson.sk.client.view.LoginDetailsView;
import org.sfnelson.sk.client.view.NavigationView;
import org.sfnelson.sk.client.view.RealmView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;

public interface Factory {

	public EventBus getEventBus();
	public PlaceController getPlaceController();
	public LadderView getLadderView();
	public RequestFactory getRequestFactory();
	public LoginDetailsView getLoginDetailsView();
	public GroupView getGroupView();
	public ApplicationView getApplicationView();
	public EventsView getEventsView();
	public GroupDetailsView getGroupDetailsView();
	public RealmView getRealmView();
	public NavigationView getNavigationView();
}
