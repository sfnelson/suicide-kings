package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface GroupView extends IsWidget {

    void setPresenter(Presenter presenter);
    void setGroups(List<GroupProxy> response);

    interface Presenter {

        void createGroup(String name, String realm, String server);
        void select(GroupProxy group);

    }
}
