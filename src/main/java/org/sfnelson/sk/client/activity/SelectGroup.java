package org.sfnelson.sk.client.activity;

import java.util.Comparator;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.GroupRequest;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.search.Index;
import org.sfnelson.sk.client.view.GroupView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SelectGroup extends AbstractActivity implements GroupView.Presenter {

	private final Realm realm;
	private final GroupView view;
	private final RequestFactory rf;
	private final PlaceController pc;

	private String filter;
	private Index<GroupProxy> index;

	public SelectGroup(Factory factory, Realm realm) {
		this.realm = realm;
		this.view = factory.getGroupView();
		this.rf = factory.getRequestFactory();
		this.pc = factory.getPlaceController();
		this.filter = "";
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);

		rf.groupRequest().findGroups(realm.getRegion().getRegion(), realm.getServer())
		.fire(new FindGroupReceiver());

		panel.setWidget(view);
	}

	@Override
	public void createGroup(String name) {
		GroupRequest rq = rf.groupRequest();
		rq.createGroup(realm.getRegion().getRegion(), realm.getServer(), name);
		rq.findGroups(realm.getRegion().getRegion(), realm.getServer())
		.to(new FindGroupReceiver());
		rq.fire();
	}

	private class FindGroupReceiver extends Receiver<List<GroupProxy>> {
		@Override
		public void onSuccess(List<GroupProxy> response) {
			if (response == null) {
				pc.goTo(realm.getRegion());
			}
			else {
				setGroups(response);
			}
		}
	}

	@Override
	public void select(GroupProxy group) {
		pc.goTo(Group.create(realm, group));
	}

	private void setGroups(List<GroupProxy> groups) {
		view.setData(groups);

		this.index = new Index<GroupProxy>(groups,
				new Index.Serializer<GroupProxy>() {
			@Override
			public String[] visit(GroupProxy realm) {
				return new String[] {};
			}
		},
		new Comparator<GroupProxy>() {
			@Override
			public int compare(GroupProxy l, GroupProxy r) {
				return l.getName().compareTo(r.getName());
			}
		});

		doFilter();
	}

	@Override
	public void filter(String filter) {
		if (this.filter.equals(filter)) {
			return;
		}

		this.filter = filter;

		doFilter();
	}

	private void doFilter() {
		final String filter = this.filter;
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (!SelectGroup.this.filter.equals(filter)) return;
				List<GroupProxy> filtered = index.filter(filter);
				if (!SelectGroup.this.filter.equals(filter)) return;
				view.showData(filtered);
			}
		});
	}
}
