package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.SuicideKingsEvent;
import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.request.CharacterProxy;
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

	private final EventsView view;
	private final Group group;

	private final Index<CharacterProxy> index = new Index<CharacterProxy>(
			new Index.Serializer<CharacterProxy>() {
				@Override
				public String[] visit(CharacterProxy input) {
					return new String[] { input.getName() };
				}
			},
			new Comparator<CharacterProxy>() {
				@Override
				public int compare(CharacterProxy a, CharacterProxy b) {
					return a.getName().compareTo(b.getName());
				}
			}
	);

	public ShowEvents(Factory factory, Group group) {
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
		List<CharacterProxy> list = index.filter(query);
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (CharacterProxy c: list) {
			suggestions.add(new CharacterSuggestion(c));
		}
		callback.onSuggestionsReady(request, new Response(suggestions));
	}

	private static class CharacterSuggestion implements Suggestion {
		private final CharacterProxy character;
		public CharacterSuggestion(CharacterProxy character) {
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
}
