package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.sfnelson.sk.client.Character;
import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.place.Realm;
import org.sfnelson.sk.client.place.Region;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.request.EventRequest;
import org.sfnelson.sk.client.request.RequestFactory;
import org.sfnelson.sk.client.search.Index;
import org.sfnelson.sk.client.view.EventsView;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.SuggestOracle.Callback;
import com.google.gwt.user.client.ui.SuggestOracle.Request;
import com.google.gwt.user.client.ui.SuggestOracle.Response;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class ShowEvents extends AbstractActivity implements EventsView.Presenter, SuicideKingsEvent.EventHandler {

	private final RequestFactory rf;
	private final EventsView view;
	private final Group group;

	private final Index<Character> index = new Index<Character>(
			new Index.Serializer<Character>() {
				@Override
				public String[] visit(Character input) {
					return new String[] { input.getName() };
				}
			},
			new Comparator<Character>() {
				@Override
				public int compare(Character a, Character b) {
					return a.getName().compareTo(b.getName());
				}
			}
	);

	public ShowEvents(Factory factory, Group group) {
		this.rf = factory.getRequestFactory();
		this.view = factory.getEventsView();
		this.group = group;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view.setPresenter(this);
		panel.setWidget(view);

		SuicideKingsEvent.register(eventBus, this);
	}

	private void addEvent(SuicideKingsEvent event) {
		view.addEvent(event);
	}

	@Override
	public void onCharacterAdded(SuicideKingsEvent event) {
		index.add(event.getCharacter());
		addEvent(event);
	}

	@Override
	public void onCharacterJoined(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void onCharacterLeft(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void onReceivedLoot(SuicideKingsEvent event) {
		addEvent(event);
	}

	@Override
	public void requestSuggestion(Request request, Callback callback) {
		String query = request.getQuery();
		List<Character> list = index.filter(query);
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (Character c: list) {
			suggestions.add(new CharacterSuggestion(c));
		}
		callback.onSuggestionsReady(request, new Response(suggestions));
	}

	private static class CharacterSuggestion implements Suggestion {
		private final Character character;
		public CharacterSuggestion(Character character) {
			this.character = character;
		}
		@Override
		public String getReplacementString() {
			return character.getName();
		}

		@Override
		public String getDisplayString() {
			return character.getName();
		}
	}

	@Override
	public void assign(String character, String loot) {
		CharacterProxy cp = null;
		List<Character> matches = index.filter(character);
		for (Character c: matches) {
			if (c.getName().toLowerCase().equals(character.toLowerCase())) {
				cp = c.getProxy();
				break;
			}
		}

		Long lootId = null;
		try {
			lootId = Long.parseLong(loot);
		}
		catch (Exception ex) {
			return;
		}

		if (cp != null && lootId != null) {
			Region region = group.getRealm().getRegion();
			Realm realm = group.getRealm();
			EventRequest rq = rf.eventRequest();
			rq.assign(region.getRegion(), realm.getServer(), group.getName(), cp.getId(), lootId).fire();
		}
	}
}
