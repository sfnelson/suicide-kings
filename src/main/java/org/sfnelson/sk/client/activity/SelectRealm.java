package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.request.RealmProxy;
import org.sfnelson.sk.client.request.RequestFactory;
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
	private final String region;

	private Index index;
	private String filter = "";

	public SelectRealm(Factory factory, String region) {
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

		rf.realmRequest().getRealms(region).fire(new Receiver<List<RealmProxy>>() {
			@Override
			public void onSuccess(List<RealmProxy> response) {
				setRealms(response);
			}
		});
	}

	@Override
	public void region(String region) {
		pc.goTo(new org.sfnelson.sk.client.place.SelectRealm(region));
	}

	@Override
	public void select(RealmProxy realm) {
		// TODO Auto-generated method stub

	}

	private void setRealms(List<RealmProxy> realms) {
		view.setRealms(realms);

		this.index = new Index(realms);

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
				view.showRealms(filtered);
			}
		});
	}

	private static class Index {
		List<RealmProxy> all;
		List<RealmProxy> emptyList;
		Set<String> emptySet;
		Map<String, Set<String>> tupleIndex;
		Map<String, List<RealmProxy>> tokenIndex;

		public Index(List<RealmProxy> realms) {
			all = realms;
			emptyList = new ArrayList<RealmProxy>();
			emptySet = new HashSet<String>();
			tupleIndex = new HashMap<String, Set<String>>();
			tokenIndex = new HashMap<String, List<RealmProxy>>();
			for (RealmProxy realm: realms) {
				addString(realm.getServer(), realm);
				addString(realm.getType(), realm);
				addString(realm.getPopulation(), realm);
				addString(realm.getLocale(), realm);
			}
		}

		private void addString(String token, RealmProxy target) {
			token = token.toLowerCase();
			if (!tokenIndex.containsKey(token)) {
				for (int i = 0; i < token.length(); i++) {
					put(token.substring(i, i + 1), token);
				}
				for (int i = 0; i < token.length() - 1; i++) {
					put(token.substring(i, i + 2), token);
				}
				for (int i = 0; i < token.length() - 2; i++) {
					put(token.substring(i, i + 3), token);
				}
			}

			put(token, target);
		}

		public List<RealmProxy> filter(String filter) {
			if (filter.length() == 0) return all;

			Set<String> tokens = tokenize(filter);

			List<RealmProxy> current = null;
			for (String token: tokens) {
				if (current == null) current = getMatches(token);
				else current = intersection(current, getMatches(token));
			}

			if (current == null) return emptyList;
			else return current;
		}

		private Set<String> tokenize(String filter) {
			Set<String> tokens = new HashSet<String>();

			filter = filter.toLowerCase();
			while (filter.indexOf(' ') != -1) {
				tokens.add(filter.substring(0, filter.indexOf(' ')));
				filter = filter.substring(filter.indexOf(' ') + 1);
			}
			tokens.add(filter);
			tokens.remove("");

			return tokens;
		}

		private List<RealmProxy> intersection(List<RealmProxy> left, List<RealmProxy> right) {
			List<RealmProxy> intersection = new ArrayList<RealmProxy>();

			int a = 0;
			int b = 0;
			while (a < left.size() && b < right.size()) {
				int comp = left.get(a).getServer().compareTo(right.get(b).getServer());
				if (comp < 0) {
					a++;
				}
				else if (comp > 0) {
					b++;
				}
				else {
					intersection.add(left.get(a));
					a++;
					b++;
				}
			}

			return intersection;
		}

		private List<RealmProxy> union(List<RealmProxy> left, List<RealmProxy> right) {
			List<RealmProxy> union = new ArrayList<RealmProxy>();

			int a = 0;
			int b = 0;
			while (a < left.size() && b < right.size()) {
				int comp = left.get(a).getServer().compareTo(right.get(b).getServer());
				if (comp < 0) {
					union.add(left.get(a));
					a++;
				}
				else if (comp > 0) {
					union.add(right.get(b));
					b++;
				}
				else {
					union.add(left.get(a));
					a++;
					b++;
				}
			}
			while (a < left.size()) {
				union.add(left.get(a++));
			}
			while (b < right.size()) {
				union.add(right.get(b++));
			}

			return union;
		}

		private Set<String> intersection(Set<String> left, Set<String> right) {
			Set<String> union = new HashSet<String>();

			for (String a: left) {
				if (right.contains(a)) {
					union.add(a);
				}
			}

			return union;
		}

		private List<RealmProxy> getMatches(String token) {
			Set<String> tokens = getTokenMatches(token);

			List<RealmProxy> realms = null;
			for (String t: tokens) {
				if (realms == null) {
					realms = tokenIndex.get(t);
				}
				else {
					realms = union(realms, tokenIndex.get(t));
				}
			}

			return realms;
		}

		private Set<String> getTokenMatches(String token) {
			if (token.length() == 1) return getTokens(token);
			if (token.length() == 2) return getTokens(token);
			if (token.length() == 3) return getTokens(token);

			Set<String> result = getTokens(token.substring(0, 3));
			for (int i = 1; i < token.length() - 3; i++) {
				result = intersection(result, getTokens(token.substring(i, i + 3)));
			}

			Set<String> filtered = new HashSet<String>();
			for (String r: result) {
				if (r.contains(token)) filtered.add(r);
			}

			return filtered;
		}

		private Set<String> getTokens(String tuple) {
			if (!tupleIndex.containsKey(tuple)) return emptySet;
			else return tupleIndex.get(tuple);
		}

		private void put(String tuple, String token) {
			Set<String> tokens;
			if (tupleIndex.containsKey(tuple)) {
				tokens = tupleIndex.get(tuple);
			}
			else {
				tokens = new HashSet<String>();
				tupleIndex.put(tuple, tokens);
			}
			tokens.add(token);
		}

		private void put(String token, RealmProxy value) {
			List<RealmProxy> values;
			if (tokenIndex.containsKey(token)) {
				values = tokenIndex.get(token);
			}
			else {
				values = new ArrayList<RealmProxy>();
				tokenIndex.put(token, values);
			}
			values.add(value);
		}
	}

	@Override
	public void refresh() {
		rf.realmRequest().refresh(region).fire(new Receiver<Void>() {
			@Override
			public void onSuccess(Void response) {
				rf.realmRequest().getRealms(region).fire(new Receiver<List<RealmProxy>>() {
					@Override
					public void onSuccess(List<RealmProxy> response) {
						setRealms(response);
					}
				});
			}
		});
	}
}
