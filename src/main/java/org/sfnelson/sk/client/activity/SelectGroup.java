package org.sfnelson.sk.client.activity;

import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.GroupSelectionEvent;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.GroupRequest;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.view.GroupView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SelectGroup extends AbstractActivity implements GroupView.Presenter {

    private final GroupView view;
    private final RequestFactory rf;
    private final EventBus eventBus;

    public SelectGroup(Factory factory) {
        this.view = factory.getGroupView();
        this.rf = factory.getRequestFactory();
        this.eventBus = factory.getEventBus();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view.setPresenter(this);

        rf.groupRequest().findGroups().with("server", "owners").fire(new FindGroupReceiver());

        panel.setWidget(view);
    }

    @Override
    public void createGroup(String name, String realm, String server) {
        GroupRequest rq = rf.groupRequest();
        rq.createGroup(name, realm, server);
        rq.findGroups().with("server", "owners").to(new FindGroupReceiver());
        rq.fire();
    }

    @Override
    public void select(GroupProxy group) {
        eventBus.fireEvent(new GroupSelectionEvent(group));
    }

    private class FindGroupReceiver extends Receiver<List<GroupProxy>> {
        @Override
        public void onSuccess(List<GroupProxy> response) {
            view.setGroups(response);
        }
    }
}
