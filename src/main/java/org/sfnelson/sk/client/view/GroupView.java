package org.sfnelson.sk.client.view;

import java.util.List;
import java.util.Set;

import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.user.client.ui.IsWidget;

public interface GroupView extends IsWidget {

	void setPresenter(Presenter presenter);
	void setData(List<GroupProxy> data);
	void showData(Set<GroupProxy> data);

	interface Presenter {

		void createGroup(String name);
		void filter(String filter);
		void select(GroupProxy group);

	}
}
