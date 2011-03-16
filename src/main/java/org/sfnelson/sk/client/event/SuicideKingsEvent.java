package org.sfnelson.sk.client.event;

import org.sfnelson.sk.client.event.SuicideKingsEvent.EventHandler;
import org.sfnelson.sk.client.request.EventProxy;

import com.google.gwt.event.shared.GwtEvent;

public class SuicideKingsEvent extends GwtEvent<EventHandler> {

    public interface EventHandler extends com.google.gwt.event.shared.EventHandler {
        public void onCharacterAdded(EventProxy event);
        public void onCharacterJoined(EventProxy event);
        public void onCharacterLeft(EventProxy event);
        public void onReceivedLoot(EventProxy event);
    }

    public static final Type<EventHandler> TYPE = new Type<EventHandler>();

	private final EventProxy event;

	public SuicideKingsEvent(EventProxy event) {
		this.event = event;
	}

    @Override
    public Type<EventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EventHandler handler) {
        switch (event.getType()) {
        case CREATED:
            handler.onCharacterAdded(event);
            break;
        case JOINED:
            handler.onCharacterJoined(event);
            break;
        case LEFT:
            handler.onCharacterLeft(event);
            break;
        case LOOT:
            handler.onReceivedLoot(event);
            break;
        }
    }
}
