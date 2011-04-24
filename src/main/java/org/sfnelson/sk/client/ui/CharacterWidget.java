package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.request.CharacterProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CharacterWidget extends Composite implements ListEntry<CharacterProxy> {

	private static CharacterWidgetUiBinder uiBinder = GWT
	.create(CharacterWidgetUiBinder.class);

	interface CharacterWidgetUiBinder extends UiBinder<Widget, CharacterWidget> {
	}

	@UiField Label name;
	@UiField Button add;
	@UiField Button remove;

	private CharacterProxy character;

	public CharacterWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public CharacterProxy getData() {
		return character;
	}

	@Override
	public void setData(CharacterProxy character) {
		this.character = character;

		name.setText(character.getName());
	}

	@Override
	public void clear() {
		name.setText("");
	}

}
