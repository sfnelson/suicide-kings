package org.sfnelson.sk.client.event;

import org.sfnelson.sk.client.event.GroupSelectionEvent.GroupSelectionHandler;
import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class GroupSelectionEvent extends GwtEvent<GroupSelectionHandler>{

    public interface GroupSelectionHandler extends EventHandler {
        void groupSelected(GroupProxy group);
    }

    public static final Type<GroupSelectionHandler> TYPE = new Type<GroupSelectionHandler>();

    public static HandlerRegistration register(EventBus eventBus, GroupSelectionHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }

    private final GroupProxy selection;

    public GroupSelectionEvent(GroupProxy selection) {
        this.selection = selection;
    }

    @Override
    public Type<GroupSelectionHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(GroupSelectionHandler handler) {
        handler.groupSelected(selection);
    }
}
