package org.sfnelson.sk.client.activity;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.GroupSelectionEvent;
import org.sfnelson.sk.client.event.GroupSelectionEvent.GroupSelectionHandler;
import org.sfnelson.sk.client.place.ShowGroup;
import org.sfnelson.sk.client.request.GaeUserProxy;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.GroupRequest;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.view.GroupDetailsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShowGroupDetails extends AbstractActivity
implements GroupDetailsView.Presenter, GroupSelectionHandler {

    private final RequestFactory rf;
    private final GroupDetailsView view;
    private final PlaceController pc;

    private GaeUserProxy user;
    private GroupProxy group;

    public ShowGroupDetails(Factory factory) {
        this.rf = factory.getRequestFactory();
        this.pc = factory.getPlaceController();
        this.view = factory.getGroupDetailsView();

        rf.loginRequest().getCurrentUser().fire(new Receiver<GaeUserProxy>() {
            @Override
            public void onSuccess(GaeUserProxy user) {
                ShowGroupDetails.this.user = user;

                refresh();
            }
        });
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view.setPresenter(this);
        GroupSelectionEvent.register(eventBus, this);
        panel.setWidget(view);
    }

    @Override
    public void onStop() {
        view.setGroup(null, false);
    }

    @Override
    public void groupSelected(GroupProxy group) {
        this.group = group;

        refresh();
    }

    private void refresh() {
        if (user != null && group != null) {
            view.setGroup(group, group.getOwners().contains(user.getUserId()));
        }
    }

    @Override
    public void select(GroupProxy group) {
        pc.goTo(ShowGroup.create(rf, group));
    }

    @Override
    public void delete(GroupProxy group) {
        GroupRequest rq = rf.groupRequest();
        rq.deleteGroup(group).fire();
    }
}
