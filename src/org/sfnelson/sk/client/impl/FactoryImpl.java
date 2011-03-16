package org.sfnelson.sk.client.impl;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.ui.ApplicationFrame;
import org.sfnelson.sk.client.ui.EventsPanel;
import org.sfnelson.sk.client.ui.GroupDetailsPanel;
import org.sfnelson.sk.client.ui.GroupPanel;
import org.sfnelson.sk.client.ui.LadderPanel;
import org.sfnelson.sk.client.ui.LoginDetails;
import org.sfnelson.sk.client.view.ApplicationView;
import org.sfnelson.sk.client.view.EventsView;
import org.sfnelson.sk.client.view.GroupDetailsView;
import org.sfnelson.sk.client.view.GroupView;
import org.sfnelson.sk.client.view.LadderView;
import org.sfnelson.sk.client.view.LoginDetailsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.RequestTransport;

public class FactoryImpl implements Factory {

    private final EventBus eventBus = new SimpleEventBus();
    private final RequestFactory requestFactory = GWT.create(RequestFactory.class);
    private final RequestTransport transport = new AuthAwareRequestTransport(eventBus);
    private final PlaceController placeController = new PlaceController(eventBus);

    private final ApplicationView app = new ApplicationFrame();
    private final LadderView ladder = new LadderPanel();
    private final LoginDetailsView loginDetails = new LoginDetails();
    private final GroupView groups = new GroupPanel();
    private final EventsView events = new EventsPanel();
    private final GroupDetailsView groupDetails = new GroupDetailsPanel();

    public FactoryImpl() {
        requestFactory.initialize(eventBus, transport);
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public PlaceController getPlaceController() {
        return placeController;
    }

    @Override
    public LadderView getLadderView() {
        return ladder;
    }

    @Override
    public RequestFactory getRequestFactory() {
        return requestFactory;
    }

    @Override
    public LoginDetailsView getLoginDetailsView() {
        return loginDetails;
    }

    @Override
    public GroupView getGroupView() {
        return groups;
    }

    @Override
    public GroupDetailsView getGroupDetailsView() {
        return groupDetails;
    }

    @Override
    public ApplicationView getApplicationView() {
        return app;
    }

    @Override
    public EventsView getEventsView() {
        return events;
    }
}
