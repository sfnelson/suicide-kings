package org.sfnelson.sk.client;

import org.sfnelson.sk.client.event.SuicideKingsEvent.EventHandler;

public interface EventManager {

    public void update();
    public void getEventHistory(EventHandler handler);

}
