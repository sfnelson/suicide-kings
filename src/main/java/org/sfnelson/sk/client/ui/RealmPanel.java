package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.place.Region;
import org.sfnelson.sk.client.request.RealmProxy;
import org.sfnelson.sk.client.view.RealmView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RealmPanel extends ListPanel<RealmProxy, RealmListEntry> implements RealmView {

	private static RealmPanelUiBinder uiBinder = GWT
	.create(RealmPanelUiBinder.class);

	interface RealmPanelUiBinder extends UiBinder<Widget, RealmPanel> {
	}

	interface Style extends CssResource {
		String hide();
	}

	private Presenter presenter;

	@UiField Style style;
	@UiField Button us;
	@UiField Button eu;
	@UiField Button refresh;
	@UiField TextBox filter;

	public RealmPanel() {
		super(new ListEntry.EntryFactory<RealmProxy, RealmListEntry>() {
			@Override
			public RealmListEntry createEntry() {
				return new RealmListEntry();
			}
		});

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		presenter.filter(filter.getValue());
	}

	@Override
	public void setRegion(Region region) {
		if (region == Region.US) {
			us.addStyleName("selected");
			eu.removeStyleName("selected");
		}
		if (region == Region.EU) {
			eu.addStyleName("selected");
			us.removeStyleName("selected");
		}
	}

	@UiHandler({"us", "eu"})
	void onRegionSelect(ClickEvent ev) {
		if (ev.getSource() == us) {
			presenter.region(Region.US);
		}
		else if (ev.getSource() == eu) {
			presenter.region(Region.EU);
		}
	}

	@UiHandler("refresh")
	void onRefresh(ClickEvent ev) {
		presenter.refresh();
	}

	@UiHandler("reset")
	void onReset(ClickEvent ev) {
		filter.setValue("");
		presenter.filter("");
	}

	@UiHandler("filter")
	void onFilterChanged(KeyUpEvent ev) {
		presenter.filter(filter.getValue());
	}

	@Override
	public void onClick(ClickEvent ev) {
		if (ev.getSource() instanceof RealmListEntry) {
			ev.preventDefault();
			ev.stopPropagation();
			RealmListEntry e = (RealmListEntry) ev.getSource();
			presenter.select(e.getData());
		}
	}
}
