package org.sfnelson.sk.client.impl;

import org.sfnelson.sk.client.event.NotAuthenticatedEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.requestfactory.client.DefaultRequestTransport;
import com.google.gwt.requestfactory.shared.ServerFailure;

public class AuthAwareRequestTransport extends DefaultRequestTransport {

    private final EventBus eventBus;

    public AuthAwareRequestTransport(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    protected RequestCallback createRequestCallback(final TransportReceiver receiver) {
        final RequestCallback callback = super.createRequestCallback(receiver);

        return new RequestCallback() {

            @Override
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == Response.SC_UNAUTHORIZED) {
                    String loginURL = response.getHeader("login");
                    if (loginURL != null) {
                        receiver.onTransportFailure(new ServerFailure(
                                "Unauthenticated user", null, null, false));
                            eventBus.fireEvent(new NotAuthenticatedEvent(loginURL));
                            return;
                    }
                }

                callback.onResponseReceived(request, response);
            }

            @Override
            public void onError(Request request, Throwable exception) {
                callback.onError(request, exception);
            }

        };
    }

}
