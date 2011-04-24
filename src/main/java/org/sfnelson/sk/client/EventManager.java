package org.sfnelson.sk.client;

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

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class EventManager extends AbstractActivity {

	private final RequestFactory rf;
	private final Timer updater = new Timer() {
		@Override
		public void run() {
			rf.eventRequest().getEvents(group, lastUpdate).fire(new EventReciever());
		}
	};

	private EventBus eventBus;
	private GroupProxy group;

	private Date lastUpdate;
	private final Queue<EventProxy> pending = new PriorityQueue<EventProxy>(128, new Comparator<EventProxy>() {
		@Override
		public int compare(EventProxy e1, EventProxy e2) {
			return e1.getDate().compareTo(e2.getDate());
		}
	});

	private final Map<Long, CharacterProxy> characters = new HashMap<Long, CharacterProxy>();
	private final Map<Long, LootProxy> loot = new HashMap<Long, LootProxy>();

	public EventManager(final RequestFactory rf, final PlaceController pc, final Group group) {
		this.rf = rf;

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

	private void init(GroupProxy group) {
		this.group = group;

		if (eventBus != null) {
			updater.scheduleRepeating(5000);
			updater.run();
		}
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
		lastUpdate = new Date(0);

		if (group != null) {
			updater.scheduleRepeating(5000);
			updater.run();
		}
	}

	@Override
	public void onStop() {
		updater.cancel();
		pending.clear();
	}

	public GroupProxy getGroup() {
		return group;
	}

	private void process() {
		while (!pending.isEmpty()) {
			EventProxy event = pending.peek();

			CharacterProxy character = characters.get(event.getCharacterId());
			if (character == null) {
				System.out.println("get character");
				getCharacter(event.getCharacterId());
				break;
			}

			LootProxy loot = null;
			if (event.getLootId() != null) {
				loot = this.loot.get(event.getLootId());
				if (loot == null) {
					System.out.println("get loot");
					getLoot(event.getLootId());
					break;
				}
			}

			pending.poll();
			SuicideKingsEvent.fire(eventBus, event, group, character, loot);
		}
	}

	private void getCharacter(Long id) {
		rf.characterRequest().findCharacter(id)
		.with("armory")
		.fire(new Receiver<CharacterProxy>() {
			@Override
			public void onSuccess(CharacterProxy character) {
				characters.put(character.getId(), character);
				process();
			}
		});
	}

	private void getLoot(Long id) {
		rf.lootRequest().findLoot(id).fire(new Receiver<LootProxy>() {
			@Override
			public void onSuccess(LootProxy loot) {
				EventManager.this.loot.put(loot.getId(), loot);
				process();
			}
		});
	}

	private class EventReciever extends Receiver<List<EventProxy>> {
		@Override
		public void onSuccess(List<EventProxy> response) {
			for (EventProxy event: response) {
				if (event.getDate().after(lastUpdate)) {
					lastUpdate = event.getDate();
					pending.offer(event);
				}
			}

			process();
		}
	}
}
