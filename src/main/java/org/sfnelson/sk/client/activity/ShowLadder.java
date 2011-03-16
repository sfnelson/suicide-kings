package org.sfnelson.sk.client.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sfnelson.sk.client.Factory;
import org.sfnelson.sk.client.event.SuicideKingsEvent.EventHandler;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.request.CharacterRequest;
import org.sfnelson.sk.client.request.EventProxy;
import org.sfnelson.sk.client.request.EventRequest;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.view.LadderView;
import org.sfnelson.sk.client.view.LadderView.LadderPresenter;
import org.sfnelson.sk.shared.Random;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.Receiver;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class ShowLadder extends AbstractActivity implements LadderPresenter, EventHandler {

    private final Factory factory;

    private GroupProxy group;

    private LadderView view;

    public ShowLadder(Factory factory, EntityProxyId<GroupProxy> groupId) {
        this.factory = factory;

        factory.getRequestFactory().find(groupId).fire(new Receiver<GroupProxy>() {

            @Override
            public void onSuccess(GroupProxy response) {
                ShowLadder.this.group = response;

                init();
            }

        });
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        view = factory.getLadderView();
        view.setPresenter(this);

        panel.setWidget(view);

        init();
    }

    @Override
    public void onStop() {
        view = null;
    }

    private void init() {
        if (view == null) return;

        // do init stuff. group may or may not be ready.
    }

    @Override
    public void registerCharacter(String name) {
        CharacterRequest rq = factory.getRequestFactory().characterRequest();
        rq.registerCharacter(group, name);
        rq.fire();
    }

    @Override
    public void joinParty(CharacterProxy character) {
        EventRequest rq = factory.getRequestFactory().eventRequest();
        rq.joinParty(group, character);
        rq.fire();
    }

    @Override
    public void leaveParty(CharacterProxy character) {
        EventRequest rq = factory.getRequestFactory().eventRequest();
        rq.leaveParty(group, character);
        rq.fire();
    }

    private void update() {
        if (view == null) return;

        List<CharacterProxy> values = new ArrayList<CharacterProxy>();
        for (CharacterProxy c: ladder) {
            if (active.contains(c)) {
                values.add(c);
            }
        }

        view.setSize(values.size());
        view.setValues(values);
    }

    private List<CharacterProxy> ladder = new ArrayList<CharacterProxy>();
    private Set<CharacterProxy> active = new HashSet<CharacterProxy>();

    @Override
    public void onCharacterAdded(EventProxy event) {
        CharacterProxy character = null; //event.getCharacter();

        if (ladder.contains(character)) {
            throw new IllegalArgumentException("Cannot add character to the ladder. Character is already present.");
        }

        int position = Random.getRandom(character.getSeed(), 0, ladder.size());
        ladder.add(position, character);

        update();
    }

    @Override
    public void onCharacterJoined(EventProxy event) {
        CharacterProxy character = null; //event.getCharacter();

        active.add(character);

        update();
    }

    @Override
    public void onCharacterLeft(EventProxy event) {
        CharacterProxy character = null; //event.getCharacter();

        active.remove(character);

        update();
    }

    @Override
    public void onReceivedLoot(EventProxy event) {
        CharacterProxy character = null; //event.getCharacter();

        int position = 0;
        while (!ladder.get(position).equals(character)) {
            if (++position >= ladder.size()) return;
        }

        while (true) { // move character down
            int next = position;
            do { // find next active character
                if (++next >= ladder.size()) return;
            } while (!active.contains(character));

            swap(position, next);

            position = next;
        }
    }

    private void swap(int a, int b) {
        CharacterProxy ca = ladder.get(a);
        CharacterProxy cb = ladder.get(b);

        ladder.set(a, cb);
        ladder.set(b, ca);
    }
}
