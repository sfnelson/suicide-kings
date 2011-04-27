package org.sfnelson.sk.client.view;

import java.util.List;
import java.util.Set;

import org.sfnelson.sk.client.place.Region;
import org.sfnelson.sk.client.request.RealmProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface RealmView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setRegion(Region region);
	void setData(List<RealmProxy> realms);
	void showData(Set<RealmProxy> realms);

	interface Presenter {
		void region(Region region);
		void select(RealmProxy realm);
		void filter(String filter);
		void refresh();
	}
}
