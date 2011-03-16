package org.sfnelson.sk.client.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface LoginDetailsView extends IsWidget {

    void setNickname(String nickname);
    void setLogoutURL(String logout);

}
