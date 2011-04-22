package org.sfnelson.sk.client.view;

import java.util.List;

import org.sfnelson.sk.client.request.RealmProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface RealmView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setRegion(String region);
	void setRealms(List<RealmProxy> realms);
	void showRealms(List<RealmProxy> realms);

	interface Presenter {
		void region(String region);
		void select(RealmProxy realm);
		void filter(String filter);
		void refresh();
	}
}
