package org.sfnelson.sk.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.LootProxy;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.shared.EventType;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EventManager extends AbstractActivity {

	private final RequestFactory rf;
	private final Updater updater;
	private final Group group;

	private final Queue<EventProxy> pending;
	private final Map<Long, Character> characters;
	private final Map<Long, LootProxy> loot;

	private EventBus eventBus;
	private GroupProxy groupProxy;

	private Date lastUpdate;

	public EventManager(final RequestFactory rf, final PlaceController pc, final Group group) {
		this.rf = rf;
		this.updater = new Updater();
		this.pending = new PriorityQueue<EventProxy>(128, new EventComparator());
		this.characters = new HashMap<Long, Character>();
		this.loot = new HashMap<Long, LootProxy>();
		this.group = group;

		final Realm realm = group.getRealm();
		rf.groupRequest().findGroup(realm.getRegion().getRegion(), realm.getServer(), group.getName())
		.fire(new Receiver<GroupProxy>() {
			@Override
			public void onSuccess(GroupProxy response) {
				if (response == null) {
					pc.goTo(realm); // group doesn't exist, select a new one.
				}
				else {
					init(response);
				}
			}
		});
	}

	private void init(final GroupProxy groupProxy) {
		Realm realm = group.getRealm();

		this.groupProxy = groupProxy;

		rf.characterRequest().findCharactersForGroup(realm.getRegion().getRegion(), realm.getServer(), group.getName())
		.with("armory")
		.fire(new Receiver<List<CharacterProxy>>() {
			@Override
			public void onSuccess(List<CharacterProxy> response) {
				for (CharacterProxy character: response) {
					if (!characters.containsKey(character.getId())) {
						Character c = new Character(rf, character, groupProxy, group);
						characters.put(character.getId(), c);
					}
				}

				if (eventBus != null) {
					updater.run();
				}
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		lastUpdate = new Date(0);

		if (groupProxy != null) {
			updater.run();
		}
	}

	@Override
	public void onStop() {
		updater.cancel();
		pending.clear();
	}

	private void process() {
		while (!pending.isEmpty()) {
			EventProxy event = pending.peek();

			Character character = characters.get(event.getCharacterId());
			if (character == null) {
				getCharacter(event.getCharacterId());
				break;
			}

			LootProxy loot = null;
			if (event.getLootId() != null) {
				loot = this.loot.get(event.getLootId());
				if (loot == null) {
					List<Long> requiredLoots = new ArrayList<Long>();
					for (EventProxy e: pending) {
						if (e.getType() == EventType.LOOT
								&& !this.loot.containsKey(e.getLootId()))
						{
							requiredLoots.add(e.getLootId());
						}
					}
					getLoot(requiredLoots);
					break;
				}
			}

			pending.poll();
			SuicideKingsEvent.fire(eventBus, event, groupProxy, character, loot);
		}
	}

	private void getCharacter(Long id) {
		rf.characterRequest().findCharacter(id)
		.with("armory")
		.fire(new Receiver<CharacterProxy>() {
			@Override
			public void onSuccess(CharacterProxy character) {
				if (!characters.containsKey(character.getId())) {
					Character c = new Character(rf, character, groupProxy, group);
					characters.put(character.getId(), c);
				}
				process();
			}
		});
	}

	private void getLoot(List<Long> requiredLoots) {
		rf.lootRequest().findLootsById(requiredLoots).fire(new Receiver<List<LootProxy>>() {
			@Override
			public void onSuccess(List<LootProxy> response) {
				for (LootProxy loot: response) {
					EventManager.this.loot.put(loot.getId(), loot);
				}
				process();
			}
		});
	}

	private class EventReciever extends Receiver<List<EventProxy>> {
		@Override
		public void onSuccess(List<EventProxy> response) {
			updater.updated(!response.isEmpty());

			for (EventProxy event: response) {
				if (event.getDate().after(lastUpdate)) {
					lastUpdate = event.getDate();
					pending.offer(event);
				}
			}

			process();
			updater.schedule();
		}
	}

	private class Updater extends Timer {
		private static final int DEFAULT_DELAY = 1000;
		private int delay = DEFAULT_DELAY;

		@Override
		public void run() {
			rf.eventRequest().getEvents(groupProxy, lastUpdate).fire(new EventReciever());
		}

		public void updated(boolean updated) {
			if (updated) {
				delay = DEFAULT_DELAY;
			}
			else {
				delay = delay * 2;
			}
		}

		public void schedule() {
			this.schedule(delay);
		}
	}

	private class EventComparator implements Comparator<EventProxy> {
		@Override
		public int compare(EventProxy e1, EventProxy e2) {
			return e1.getDate().compareTo(e2.getDate());
		}
	}
}
