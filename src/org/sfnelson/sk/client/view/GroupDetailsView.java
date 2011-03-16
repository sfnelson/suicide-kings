package org.sfnelson.sk.client.view;

import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface GroupDetailsView extends IsWidget {

    void setPresenter(Presenter presenter);
    void setGroup(GroupProxy group, boolean isOwner);

    public interface Presenter {
        void select(GroupProxy group);
        void delete(GroupProxy group);
    }
}
