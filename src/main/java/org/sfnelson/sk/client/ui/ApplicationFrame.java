package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.view.ApplicationView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class ApplicationFrame extends Composite implements ApplicationView {

	private static LayoutUiBinder uiBinder = GWT.create(LayoutUiBinder.class);

	interface LayoutUiBinder extends UiBinder<Widget, ApplicationFrame> {
	}

	public ApplicationFrame() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField SimplePanel login;
	@UiField SimplePanel menu;
	@UiField SimplePanel navigation;
	@UiField SimplePanel selection;
	@UiField SimplePanel information;

	@Override
	public AcceptsOneWidget getInformationContainer() {
		return information;
	}

	@Override
	public AcceptsOneWidget getSelectionContainer() {
		return selection;
	}

	@Override
	public AcceptsOneWidget getLoginContainer() {
		return login;
	}

	@Override
	public AcceptsOneWidget getMenuContainer() {
		return menu;
	}

	@Override
	public AcceptsOneWidget getNavContainer() {
		return navigation;
	}
}
