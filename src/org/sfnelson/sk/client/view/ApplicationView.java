package org.sfnelson.sk.client.view;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;

public interface ApplicationView extends IsWidget {

    AcceptsOneWidget getInformationContainer();
    AcceptsOneWidget getSelectionContainer();
    AcceptsOneWidget getLoginContainer();
    AcceptsOneWidget getMenuContainer();

}
