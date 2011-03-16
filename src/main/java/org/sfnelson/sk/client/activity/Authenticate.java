package org.sfnelson.sk.client.activity;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.request.GaeUser;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class Authenticate extends AbstractActivity {

    private final Factory factory;

    public Authenticate(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        System.out.println("requesting login details");
        factory.getRequestFactory().loginRequest().getCurrentUser()
        .fire(new Receiver<GaeUser>() {

            @Override
            public void onSuccess(GaeUser response) {
                Authenticate.this.onSuccess(response);
            }

        });
    }

    private void onSuccess(GaeUser user) {
        System.out.println("received login details");
        if (user != null) {
            System.out.println("hi " + user.getNickname());
            //factory.getPlaceController().goTo(new LadderPlace(null));
        }
        else {
            factory.getRequestFactory().loginRequest().createLoginURL(Window.Location.getHref())
            .fire(new Receiver<String>() {
                @Override
                public void onSuccess(String response) {
                    Window.Location.assign(response);
                }
            });
        }
    }

}
