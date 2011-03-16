package org.sfnelson.sk.client.event;

import org.sfnelson.sk.client.event.NotAuthenticatedEvent.AuthenticationHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public class NotAuthenticatedEvent extends GwtEvent<AuthenticationHandler> {

    public interface AuthenticationHandler extends EventHandler {
        public void onAuthenticationRequired(String loginURL);
    }

    public static HandlerRegistration register(EventBus eventBus, AuthenticationHandler handler) {
        return eventBus.addHandler(TYPE, handler);
    }
    public static final Type<AuthenticationHandler> TYPE = new Type<AuthenticationHandler>();

    private final String loginURL;

    public NotAuthenticatedEvent(String loginURL) {
        this.loginURL = loginURL;
    }

    @Override
    public Type<AuthenticationHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(AuthenticationHandler handler) {
        handler.onAuthenticationRequired(loginURL);
    }
}
