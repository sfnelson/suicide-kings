package org.sfnelson.sk.client.ui;

import java.util.List;

import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.view.GroupView;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

public class GroupPanel extends Composite implements GroupView {

    private static GroupPanelUiBinder uiBinder = GWT.create(GroupPanelUiBinder.class);

    interface GroupPanelUiBinder extends UiBinder<Widget, GroupPanel> {
    }

    public GroupPanel() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField CellTable<GroupProxy> groups;

    @UiField TextBox name;
    @UiField TextBox realm;
    @UiField TextBox server;

    @UiField Button create;

    private Presenter presenter;

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setGroups(List<GroupProxy> groups) {
        this.groups.setRowData(groups);
    }

    @UiHandler("create")
    void onCreate(ClickEvent e) {
        presenter.createGroup(name.getValue(), realm.getValue(), server.getValue());
    }

    @UiFactory
    CellTable<GroupProxy> createGroupTable() {
        CellTable<GroupProxy> table = new CellTable<GroupProxy>();

        table.addColumn(new Column<GroupProxy, String>(new TextCell()) {
            @Override
            public String getValue(GroupProxy group) {
                return group.getName();
            }
        });

        table.addColumn(new Column<GroupProxy, String>(new TextCell()) {
            @Override
            public String getValue(GroupProxy group) {
                if (group == null || group.getServer() == null) {
                    return "null";
                }
                else {
                    return group.getServer().getRealm();
                }
            }
        });

        table.addColumn(new Column<GroupProxy, String>(new TextCell()) {
            @Override
            public String getValue(GroupProxy group) {
                if (group == null || group.getServer() == null) {
                    return "null";
                }
                else {
                    return group.getServer().getServer();
                }
            }
        });

        table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
        final SingleSelectionModel<GroupProxy> selectionModel = new SingleSelectionModel<GroupProxy>();
        table.setSelectionModel(selectionModel);
        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
            @Override
            public void onSelectionChange(SelectionChangeEvent event) {
                presenter.select(selectionModel.getSelectedObject());
            }
        });

        return table;
    }
}
