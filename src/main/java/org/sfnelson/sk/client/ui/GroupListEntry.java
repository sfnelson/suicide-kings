package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GroupListEntry extends Composite implements ListEntry<GroupProxy> {

	private static GroupListEntryUiBinder uiBinder = GWT
	.create(GroupListEntryUiBinder.class);

	interface GroupListEntryUiBinder extends UiBinder<Widget, GroupListEntry> {
	}

	@UiField Label name;

	private GroupProxy group;

	public GroupListEntry() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public GroupProxy getData() {
		return group;
	}

	@Override
	public void setData(GroupProxy group) {
		this.group = group;

		name.setText(group.getName());
	}

	@Override
	public void clear() {
		name.setText("");
	}

}
