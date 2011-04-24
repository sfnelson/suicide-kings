package org.sfnelson.sk.client.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sfnelson.sk.client.view.NavigationView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class BreadcrumbPanel extends Composite implements NavigationView, ClickHandler {

	private static BreadcrumbPanelUiBinder uiBinder = GWT
	.create(BreadcrumbPanelUiBinder.class);

	interface BreadcrumbPanelUiBinder extends UiBinder<Widget, BreadcrumbPanel> {
	}

	interface Style extends CssResource {
		String crumb();
		String selected();
		String spacer();
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
			a.setStyleName(style.crumb());
			a.setText(text);
			a.setHref("#" + target.toString());
			a.addClickHandler(this);
			this.targets.put(a, target);
			container.add(a);

			if (i + 1 == texts.size()) {
				a.addStyleName(style.selected());
			}
			else {
				Label l = new Label(">");
				l.addStyleName(style.spacer());
				container.add(l);
			}
		}
	}

	@Override
	public void onClick(ClickEvent ev) {
		if (ev.getSource() instanceof Anchor) {
			ev.preventDefault();
			ev.stopPropagation();
			Place target = targets.get(ev.getSource());
			if (target != null) {
				presenter.select(target);
			}
		}
	}
}
