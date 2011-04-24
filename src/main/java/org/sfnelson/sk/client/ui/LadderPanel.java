package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.ui.ListEntry.EntryFactory;
import org.sfnelson.sk.client.view.LadderView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LadderPanel extends ListPanel<CharacterProxy, CharacterWidget> implements LadderView {

	private static LadderPanelUiBinder uiBinder = GWT.create(LadderPanelUiBinder.class);

	interface LadderPanelUiBinder extends UiBinder<Widget, LadderPanel> {}

	interface Style extends CssResource {
		String editMode();
		String viewMode();
		String edit();
		String view();
	}

	@UiField Style style;
	@UiField TextBox name;
	@UiField Button edit;
	@UiField Button add;
	@UiField Button done;

	private LadderPresenter presenter;

	public LadderPanel() {
		super(new EntryFactory<CharacterProxy, CharacterWidget>() {
			@Override
			public CharacterWidget createEntry() {
				return new CharacterWidget();
			}
		});

		initWidget(uiBinder.createAndBindUi(this));
		addStyleName(style.viewMode());
	}

	@Override
	public void setPresenter(LadderPresenter presenter) {
		this.presenter = presenter;
	}

	@UiHandler("add")
	void add(ClickEvent e) {
		if (name.getValue() != null) {
			presenter.registerCharacter(name.getValue());
		}
	}

	@UiHandler("edit")
	void edit(ClickEvent e) {
		removeStyleName(style.viewMode());
		addStyleName(style.editMode());

		presenter.setShowAll(true);
	}

	@UiHandler("done")
	void done(ClickEvent e) {
		removeStyleName(style.editMode());
		addStyleName(style.viewMode());

		presenter.setShowAll(false);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub

	}
}
