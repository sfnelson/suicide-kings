package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.request.EventProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface EventsView extends IsWidget {

    void setPresenter(Presenter presenter);
    void setEvents(List<EventProxy> response);

    public interface Presenter {

    }
}
