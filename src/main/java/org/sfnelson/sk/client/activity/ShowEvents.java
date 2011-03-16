package org.sfnelson.sk.client.activity;

import java.util.Date;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.request.EventRequest;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.view.EventsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShowEvents extends AbstractActivity implements EventsView.Presenter {

    private final Factory factory;
    private final EventsView view;
    private final EntityProxyId<GroupProxy> groupId;

    private Date lastUpdate;

    public ShowEvents(Factory factory, EntityProxyId<GroupProxy> groupId) {
        this.factory = factory;
        this.view = factory.getEventsView();
        this.groupId = groupId;

        lastUpdate = new Date(0);
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view.setPresenter(this);

        refresh();

        panel.setWidget(view);

        new Timer() {
            @Override
            public void run() {
                refresh();
            }
        }.scheduleRepeating(5000);
    }

    private void refresh() {
        EventRequest rq = factory.getRequestFactory().eventRequest();
        rq.getEvents(lastUpdate).fire(new Receiver<List<EventProxy>>() {
            @Override
            public void onSuccess(List<EventProxy> response) {
                view.setEvents(response);
            }
        });
    }
}
