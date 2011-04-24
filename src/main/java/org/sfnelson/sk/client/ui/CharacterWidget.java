package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.Character;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CharacterWidget extends Composite implements ListEntry<Character> {

	private static CharacterWidgetUiBinder uiBinder = GWT
	.create(CharacterWidgetUiBinder.class);

	interface CharacterWidgetUiBinder extends UiBinder<Widget, CharacterWidget> {
	}

	interface Style extends CssResource {
		String present();
	}

	@UiField Style style;
	@UiField Image avatar;
	@UiField Label name;
	@UiField Button add;
	@UiField Button remove;

	private Character character;

	public CharacterWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public Character getData() {
		return character;
	}

	@Override
	public void setData(Character character) {
		this.character = character;

		name.setText(character.getName());
		avatar.setUrl(character.getAvatar());

		if (character.isPresent()) {
			addStyleName(style.present());
		}
		else {
			removeStyleName(style.present());
		}
	}

	@Override
	public void clear() {
		name.setText("");
		avatar.setUrl("");
		removeStyleName(style.present());
	}

	@UiHandler("add")
	void join(ClickEvent ev) {
		character.join();
	}

	@UiHandler("remove")
	void leave(ClickEvent ev) {
		character.leave();
	}
}
