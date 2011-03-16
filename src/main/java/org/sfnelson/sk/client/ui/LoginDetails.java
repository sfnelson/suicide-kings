package org.sfnelson.sk.client.ui;

import org.sfnelson.sk.client.view.LoginDetailsView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class LoginDetails extends Composite implements LoginDetailsView {

    private static LoginDetailsUiBinder uiBinder = GWT.create(LoginDetailsUiBinder.class);

    interface LoginDetailsUiBinder extends UiBinder<Widget, LoginDetails> {
    }

    public LoginDetails() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField Label name;
    @UiField Anchor logout;

    public void setNickname(String nickname) {
        name.setText(nickname);
    }

    public void setLogoutURL(String logout) {
        this.logout.setHref(logout);
    }

}
