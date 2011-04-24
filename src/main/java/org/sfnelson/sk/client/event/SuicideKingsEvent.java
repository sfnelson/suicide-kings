package org.sfnelson.sk.client.event;

import org.sfnelson.sk.client.event.SuicideKingsEvent.EventHandler;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.LootProxy;
import org.sfnelson.sk.shared.EventType;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class SuicideKingsEvent extends GwtEvent<EventHandler> {

	public static void fire(EventBus bus, EventProxy event, GroupProxy group, CharacterProxy character, LootProxy loot) {
		bus.fireEvent(new SuicideKingsEvent(event, group, character, loot));
	}

	public static HandlerRegistration register(EventBus eventBus, EventHandler handler) {
		return eventBus.addHandler(TYPE, handler);
	}

	public interface EventHandler extends com.google.gwt.event.shared.EventHandler {
		public void onCharacterAdded(SuicideKingsEvent event);
		public void onCharacterJoined(SuicideKingsEvent event);
		public void onCharacterLeft(SuicideKingsEvent event);
		public void onReceivedLoot(SuicideKingsEvent event);
	}

	public static final Type<EventHandler> TYPE = new Type<EventHandler>();

	private final EventProxy event;
	private final GroupProxy group;
	private final CharacterProxy character;
	private final LootProxy loot;

	private SuicideKingsEvent(EventProxy event, GroupProxy group, CharacterProxy character, LootProxy loot) {
		this.event = event;
		this.group = group;
		this.character = character;
		this.loot = loot;
	}

	public EventType getType() {
		return event.getType();
	}

	public EventProxy getEvent() {
		return event;
	}

	public GroupProxy getGroup() {
		return group;
	}

	public CharacterProxy getCharacter() {
		return character;
	}

	public LootProxy getLoot() {
		return loot;
	}

	@Override
	public Type<EventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EventHandler handler) {
		switch (event.getType()) {
		case ADDED:
			handler.onCharacterAdded(this);
			break;
		case JOINED:
			handler.onCharacterJoined(this);
			break;
		case LEFT:
			handler.onCharacterLeft(this);
			break;
		case LOOT:
			handler.onReceivedLoot(this);
			break;
		}
	}
}
