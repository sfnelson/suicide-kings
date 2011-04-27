package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.view.EventsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class EventsPanel extends Composite implements EventsView {

	private static EventsPanelUiBinder uiBinder = GWT.create(EventsPanelUiBinder.class);

	interface EventsPanelUiBinder extends UiBinder<Widget, EventsPanel> {
	}

	interface Style extends CssResource {
		String added();
		String joined();
		String left();
		String loot();
	}

	@UiField Style style;

	@UiField SuggestBox name;
	@UiField TextBox lootId;
	@UiField Button submit;

	@UiField Anchor added;
	@UiField Anchor joined;
	@UiField Anchor left;
	@UiField Anchor loot;

	@UiField FlowPanel list;

	private Presenter presenter;

	public EventsPanel() {
		initWidget(uiBinder.createAndBindUi(this));

		addStyleName(style.loot());
	}

	@UiFactory
	SuggestBox suggest() {
		return new SuggestBox(new SuggestOracle() {
			@Override
			public void requestSuggestions(Request request, Callback callback) {
				presenter.requestSuggestion(request, callback);
			}
		});
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		list.clear();
	}

	@Override
	public void addEvent(SuicideKingsEvent event) {
		list.insert(new EventWidget(event), 0);
	}

	@UiHandler({"added", "joined", "left", "loot"})
	void toggle(ClickEvent ev) {
		if (!(ev.getSource() instanceof Anchor)) return;
		Anchor a = (Anchor) ev.getSource();
		String style = a.getStyleName();

		if (getStyleName().contains(style)) {
			removeStyleName(style);
		}
		else {
			addStyleName(style);
		}
	}

	@UiHandler("submit")
	void assign(ClickEvent ev) {
		presenter.assign(name.getValue(), lootId.getValue());
	}

	private class EventWidget extends HTML {
		private final DateTimeFormat format = DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT);

		public EventWidget(SuicideKingsEvent event) {
			setStyleName("item");

			String date = "<span class='date'>" + format.format(event.getEvent().getDate()) + "</span>";
			String name = event.getCharacter().getHref();
			String type = "";
			String loot = "";

			switch (event.getType()) {
			case ADDED:
				type = " join the group.";
				addStyleName(style.added());
				break;
			case JOINED:
				type = " joined the raid.";
				addStyleName(style.joined());
				break;
			case LEFT:
				type = " left the raid.";
				addStyleName(style.left());
				break;
			case LOOT:
				type = " received loot ";
				loot = event.getLoot().getName() + " (" + event.getLoot().getReference() + ")";
				loot = "<a href='http://www.wowhead.com/?item=" + event.getLoot().getReference() + "'>"
				+ event.getLoot().getName() + "</a>";
				addStyleName(style.loot());
				break;
			}

			setHTML(date + " " + name + type + loot);
		}
	}
}
