package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.request.RealmProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class RealmListEntry extends Composite implements ListEntry<RealmProxy> {

	private static RealmListEntryUiBinder uiBinder = GWT
	.create(RealmListEntryUiBinder.class);

	interface RealmListEntryUiBinder extends UiBinder<Widget, RealmListEntry> {
	}

	interface Style extends CssResource {
		String pvp();
		String pve();
		String rp();
		String rppvp();
		String low();
		String medium();
		String high();
		String full();
	}

	@UiField Style style;
	@UiField Label server;
	@UiField Label type;
	@UiField Label population;
	@UiField Label locale;

	private RealmProxy realm;

	public RealmListEntry() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setData(RealmProxy realm) {
		this.realm = realm;

		server.setText(realm.getServer());

		String type = realm.getType();
		if (type != null) {
			if (type.equals("pvp")) {
				this.type.setText("(PvP)");
				this.type.addStyleName(style.pvp());
			}
			else if (type.equals("pve")) {
				this.type.setText("(PvE)");
				this.type.addStyleName(style.pve());
			}
			else if (type.equals("rp")) {
				this.type.setText("(RP)");
				this.type.addStyleName(style.rp());
			}
			else if (type.equals("rppvp")) {
				this.type.setText("(RP PvP)");
				this.type.addStyleName(style.rppvp());
			}
		}

		String pop = realm.getPopulation();
		if (pop != null) {
			if (pop.equals("low")) {
				this.population.setText("Low");
				this.population.addStyleName(style.low());
			}
			else if (pop.equals("medium")) {
				this.population.setText("Medium");
				this.population.addStyleName(style.medium());
			}
			else if (pop.equals("high")) {
				this.population.setText("High");
				this.population.addStyleName(style.high());
			}
			else if (pop.equals("full")) {
				this.population.setText("Full");
				this.population.addStyleName(style.full());
			}
		}

		String locale = realm.getLocale();
		if (locale != null) {
			this.locale.setText(locale);
		}
	}

	@Override
	public void clear() {
		server.setText("");

		type.setText("");
		type.removeStyleName(style.pvp());
		type.removeStyleName(style.pve());
		type.removeStyleName(style.rp());
		type.removeStyleName(style.rppvp());

		population.setText("");
		population.removeStyleName(style.low());
		population.removeStyleName(style.medium());
		population.removeStyleName(style.high());
		population.removeStyleName(style.full());

		locale.setText("");
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return addDomHandler(handler, ClickEvent.getType());
	}

	@Override
	public RealmProxy getData() {
		return realm;
	}

}
