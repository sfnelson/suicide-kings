package org.sfnelson.sk.client.activity;

import java.util.Comparator;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.place.Region;
import org.sfnelson.sk.client.request.RealmProxy;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.search.Index;
import org.sfnelson.sk.client.view.RealmView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class SelectRealm extends AbstractActivity implements RealmView.Presenter {

	private final RealmView view;
	private final RequestFactory rf;
	private final PlaceController pc;
	private final Region region;

	private Index<RealmProxy> index;
	private String filter = "";

	public SelectRealm(Factory factory, Region region) {
		this.view = factory.getRealmView();
		this.rf = factory.getRequestFactory();
		this.pc = factory.getPlaceController();
		this.region = region;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		view.setRegion(region);
		panel.setWidget(view);

		rf.realmRequest().getRealms(region.getRegion()).fire(new Receiver<List<RealmProxy>>() {
			@Override
			public void onSuccess(List<RealmProxy> response) {
				setRealms(response);
			}
		});
	}

	@Override
	public void region(Region region) {
		pc.goTo(region);
	}

	@Override
	public void select(RealmProxy realm) {
		pc.goTo(Realm.parse(realm.getRegion(), realm.getServer()));
	}

	private void setRealms(List<RealmProxy> realms) {
		view.setData(realms);

		this.index = new Index<RealmProxy>(realms,
				new Index.Serializer<RealmProxy>() {
			@Override
			public String[] visit(RealmProxy realm) {
				return new String[] {
						realm.getServer(),
						realm.getType(),
						realm.getPopulation(),
						realm.getLocale()
				};
			}
		},
		new Comparator<RealmProxy>() {
			@Override
			public int compare(RealmProxy l, RealmProxy r) {
				return l.getServer().compareTo(r.getServer());
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
				if (!SelectRealm.this.filter.equals(filter)) return;
				List<RealmProxy> filtered = index.filter(filter);
				if (!SelectRealm.this.filter.equals(filter)) return;
				view.showData(filtered);
			}
		});
	}

	@Override
	public void refresh() {
		rf.realmRequest().refresh(region.getRegion()).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				rf.realmRequest().getRealms(region.getRegion()).fire(new Receiver<List<RealmProxy>>() {
					@Override
					public void onSuccess(List<RealmProxy> response) {
						setRealms(response);
					}
				});
			}
		});
	}
}
