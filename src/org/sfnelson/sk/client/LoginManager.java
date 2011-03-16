package org.sfnelson.sk.client;

import org.sfnelson.sk.client.request.GaeRequest;
import org.sfnelson.sk.client.request.GaeUser;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.view.LoginDetailsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class LoginManager extends AbstractActivity {

    private final RequestFactory rf;
    private final LoginDetailsView view;

    public LoginManager(Factory factory) {
        rf = factory.getRequestFactory();
        view = factory.getLoginDetailsView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);

        GaeRequest request = rf.loginRequest();
        request.getCurrentUser().to(new Receiver<GaeUser>() {
            @Override
            public void onSuccess(GaeUser user) {
                view.setNickname(user.getNickname());
            }
        });
        request.createLogoutURL(Window.Location.getHref()).to(new Receiver<String>() {
            @Override
            public void onSuccess(String url) {
                view.setLogoutURL(url);
            }
        });
        request.fire();
    }
}
