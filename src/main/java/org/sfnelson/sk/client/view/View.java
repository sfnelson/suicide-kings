package org.sfnelson.sk.client.view;

import org.sfnelson.sk.client.view.View.ViewPresenter;

import com.google.gwt.user.client.ui.IsWidget;

public interface View<T extends ViewPresenter> extends IsWidget {

    void setPresenter(T presenter);

    public interface ViewPresenter {

    }
}
