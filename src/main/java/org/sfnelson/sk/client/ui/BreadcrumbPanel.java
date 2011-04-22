package org.sfnelson.sk.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sfnelson.sk.client.view.NavigationView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BreadcrumbPanel extends Composite implements NavigationView {

	private static BreadcrumbPanelUiBinder uiBinder = GWT
	.create(BreadcrumbPanelUiBinder.class);

	interface BreadcrumbPanelUiBinder extends UiBinder<Widget, BreadcrumbPanel> {
	}

	interface Style extends CssResource {
		String crumb();
		String selected();
	}

	private final Map<Anchor, Place> targets = new HashMap<Anchor, Place>();

	@UiField Style style;
	@UiField Panel container;

	private Presenter presenter;

	public BreadcrumbPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setTrail(List<String> texts, List<Place> targets) {
		this.container.clear();
		this.targets.clear();

		for (int i = 0; i < texts.size(); i++) {
			String text = texts.get(i);
			Place target = targets.get(i);

			Anchor a = new Anchor();
			a.setText(text);
			a.setTarget(target.toString());
			this.targets.put(a, target);

			if (i + 1 == texts.size()) {
				a.addStyleName(style.selected());
			}
		}
	}

	@UiHandler("wrapper")
	void onClick(ClickEvent ev) {
		if (ev.getSource() instanceof Anchor) {
			Place target = targets.get(ev.getSource());
			if (target != null) {
				ev.preventDefault();
				ev.stopPropagation();
				presenter.select(target);
			}
		}
	}
}
