package org.sfnelson.sk.client.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.event.SuicideKingsEvent.EventHandler;
import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.request.EventRequest;
import org.sfnelson.sk.client.request.RequestFactory;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Timer;

public class EventManagerImpl extends Receiver<List<EventProxy>> {

    private final RequestFactory requestFactory;
    private final EventBus eventBus;

    private final List<EventProxy> events;

    private Date lastUpdate;

    public EventManagerImpl(Factory factory) {
        this.requestFactory = factory.getRequestFactory();
        this.eventBus = factory.getEventBus();

        this.events = new ArrayList<EventProxy>();

        lastUpdate = new Date(0);

        new Timer() {
            @Override
            public void run() {
                update();
            }
        }.scheduleRepeating(5000);
    }

    public void update() {
        EventRequest rq = requestFactory.eventRequest();
        rq.getEvents(lastUpdate).fire(this);
    }

    public void getEventHistory(EventHandler handler) {
        for (EventProxy e: events) {
            switch (e.getType()) {
            case CREATED:
                handler.onCharacterAdded(e);
                break;
            case JOINED:
                handler.onCharacterJoined(e);
                break;
            case LEFT:
                handler.onCharacterLeft(e);
                break;
            case LOOT:
                handler.onReceivedLoot(e);
                break;
            }
        }
    }

    @Override
    public void onSuccess(List<EventProxy> response) {
        for (EventProxy event: response) {
            if (event.getDate().after(lastUpdate)) {
                lastUpdate = event.getDate();

                eventBus.fireEvent(new SuicideKingsEvent(event));
            }
        }
    }
}
