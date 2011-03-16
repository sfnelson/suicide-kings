package org.sfnelson.sk.client;

import org.sfnelson.sk.client.view.ApplicationView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SuicideKingsApplication extends AbstractActivity {

    private final Factory factory;
    private final ApplicationView view;

    public SuicideKingsApplication(Factory factory) {
        this.factory = factory;
        this.view = factory.getApplicationView();
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        new LoginManager(factory).start(view.getLoginContainer(), eventBus);

        SelectionActivityMapper selectionMapper = new SelectionActivityMapper(factory);
        new ActivityManager(selectionMapper, eventBus).setDisplay(view.getSelectionContainer());

        InformationActivityMapper infoMapper = new InformationActivityMapper(factory);
        new ActivityManager(infoMapper, eventBus).setDisplay(view.getInformationContainer());

        panel.setWidget(view);
    }

}
