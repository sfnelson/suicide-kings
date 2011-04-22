package org.sfnelson.sk.client.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sfnelson.sk.client.request.RealmProxy;
import org.sfnelson.sk.client.view.RealmView;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RealmPanel extends Composite implements RealmView {

	private static RealmPanelUiBinder uiBinder = GWT
	.create(RealmPanelUiBinder.class);

	interface RealmPanelUiBinder extends UiBinder<Widget, RealmPanel> {
	}

	interface Style extends CssResource {
		String hide();
	}

	private final List<RealmProxy> realmList = new ArrayList<RealmProxy>();
	private final Map<RealmProxy, RealmListEntry> realmMap = new HashMap<RealmProxy, RealmListEntry>();
	private final List<RealmListEntry> free = new ArrayList<RealmListEntry>();

	private Presenter presenter;

	@UiField Style style;
	@UiField Button us;
	@UiField Button eu;
	@UiField Button refresh;
	@UiField TextBox filter;
	@UiField FlowPanel realms;

	public RealmPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;

		presenter.filter(filter.getValue());
	}

	@Override
	public void setRealms(List<RealmProxy> realms) {
		for (int i = 0; i < realms.size(); i++) {
			RealmProxy updated = realms.get(i);

			if (i < realmList.size()) {
				RealmProxy current = realmList.get(i);
				if (updated.equals(current)) {
					realmMap.get(updated).clear();
					realmMap.get(updated).setRealm(updated);
					continue;
				}
				else {
					while (realmList.size() > i) {
						removeRealmEntry(realmList.remove(realmList.size() - 1));
					}
				}
			}

			realmList.add(updated);
			this.realms.add(createRealmEntry(updated));
		}
	}

	@Override
	public void showRealms(List<RealmProxy> realms) {
		for (RealmProxy realm: realmList) {
			realmMap.get(realm).addStyleName(style.hide());
		}

		int i = 0;
		for (RealmProxy realm: realms) {
			RealmListEntry e = realmMap.get(realm);
			e.removeStyleName(style.hide());
			if (i++ % 2 == 1) {
				e.removeStyleName("even");
				e.addStyleName("odd");
			}
			else {
				e.removeStyleName("odd");
				e.addStyleName("even");
			}
		}
	}

	@UiFactory
	CellList<RealmProxy> createList() {
		Cell<RealmProxy> cell = new AbstractCell<RealmProxy>() {

			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					RealmProxy value, SafeHtmlBuilder sb) {
				sb.appendEscaped(value.getServer());
			}

		};
		return new CellList<RealmProxy>(cell);
	}

	@Override
	public void setRegion(String region) {
		if (region.equals("us")) {
			us.addStyleName("selected");
			eu.removeStyleName("selected");
		}
		if (region.equals("eu")) {
			eu.addStyleName("selected");
			us.removeStyleName("selected");
		}
	}

	@UiHandler({"us", "eu"})
	void onRegionSelect(ClickEvent ev) {
		if (ev.getSource() == us) {
			presenter.region("us");
		}
		else if (ev.getSource() == eu) {
			presenter.region("eu");
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

	private RealmListEntry createRealmEntry(RealmProxy realm) {
		RealmListEntry entry;
		if (free.isEmpty()) {
			entry = new RealmListEntry();
		}
		else {
			entry = free.remove(free.size() - 1);
		}
		entry.setRealm(realm);
		realmMap.put(realm, entry);
		entry.removeStyleName(style.hide());

		return entry;
	}

	private void removeRealmEntry(RealmProxy realm) {
		RealmListEntry entry = realmMap.get(realm);
		if (entry == null) return;

		entry.clear();
		entry.addStyleName(style.hide());
		entry.removeStyleName("odd");
		entry.removeStyleName("even");
		realmMap.remove(realm);
		free.add(entry);
	}
}
