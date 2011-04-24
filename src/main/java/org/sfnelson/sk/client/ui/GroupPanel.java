package org.sfnelson.sk.client.ui;

import java.util.List;

import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.view.GroupView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class GroupPanel extends ListPanel<GroupProxy, GroupListEntry> implements GroupView {

	private static GroupPanelUiBinder uiBinder = GWT.create(GroupPanelUiBinder.class);

	interface GroupPanelUiBinder extends UiBinder<Widget, GroupPanel> {
	}

	public GroupPanel() {
		super(new ListEntry.EntryFactory<GroupProxy, GroupListEntry>() {
			@Override
			public GroupListEntry createEntry() {
				return new GroupListEntry();
			}
		});

		initWidget(uiBinder.createAndBindUi(this));
		create.addStyleName("disable");
	}

	@UiField TextBox filter;
	@UiField Button create;
	@UiField Button reset;

	private boolean unique = false;

	private Presenter presenter;

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		presenter.filter(filter.getValue());
	}

	@Override
	public void showData(List<GroupProxy> data) {
		super.showData(data);

		if (data.isEmpty() && filter.getValue().length() >= 5) {
			create.removeStyleName("disable");
			unique = true;
		}
		else {
			create.addStyleName("disable");
			unique = false;
		}
	}

	@UiHandler("create")
	void onCreate(ClickEvent e) {
		if (unique) {
			presenter.createGroup(filter.getValue());
		}
	}

	@UiHandler("filter")
	void onChange(KeyUpEvent ev) {
		presenter.filter(filter.getValue());
	}

	@UiHandler("reset")
	void reset(ClickEvent ev) {
		filter.setValue("");
		presenter.filter("");
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof GroupListEntry) {
			GroupListEntry e = (GroupListEntry) event.getSource();
			event.preventDefault();
			event.stopPropagation();
			presenter.select(e.getData());
		}
	}
}
