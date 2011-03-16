package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.view.GroupDetailsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class GroupDetailsPanel extends Composite implements GroupDetailsView {

    private static GroupDetailsPanelUiBinder uiBinder = GWT.create(GroupDetailsPanelUiBinder.class);

    interface GroupDetailsPanelUiBinder extends UiBinder<Widget, GroupDetailsPanel> {
    }

    interface Style extends CssResource {
        String owner();
        String show();
    }

    private Presenter presenter;
    private GroupProxy group;

    @UiField Panel panel;
    @UiField Style style;
    @UiField Label name;
    @UiField Label realm;
    @UiField Label server;
    @UiField Button select;
    @UiField Button delete;

    public GroupDetailsPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;

        panel.removeStyleName(style.owner());
    }

    @Override
    public void setGroup(GroupProxy group, boolean isOwner) {
        this.group = group;

        if (group == null) {
            panel.removeStyleName(style.show());
            return;
        }
        else {
            panel.addStyleName(style.show());
        }

        name.setText(group.getName());

        if (group.getServer() == null) {
            realm.setText("null");
            server.setText("null");
        }
        else {
            realm.setText(group.getServer().getRealm());
            server.setText(group.getServer().getServer());
        }

        if (isOwner) {
            panel.addStyleName(style.owner());
        }
        else {
            panel.removeStyleName(style.owner());
        }
    }

    @UiHandler("select")
    void select(ClickEvent event) {
        presenter.select(group);
    }

    @UiHandler("delete")
    void delete(ClickEvent event) {
        presenter.delete(group);
    }
}
