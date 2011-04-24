package org.sfnelson.sk.client;

import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.view.LadderView.LadderPresenter;

public class Character {

	private final LadderPresenter presenter;

	private final CharacterProxy characterProxy;
	private final Group group;

	private boolean present = false;

	public Character(CharacterProxy characterProxy, Group group, LadderPresenter presenter) {
		this.presenter = presenter;
		this.characterProxy = characterProxy;
		this.group = group;
	}

	public String getName() {
		return characterProxy.getName();
	}

	public String getAvatar() {
		String url = "http://us.battle.net/static-render/"
			+ group.getRealm().getRegion().getRegion()
			+ "/" + group.getRealm().getServer()
			+ "/" + characterProxy.getArmory().getArmoryHash()
			+ "/" + characterProxy.getArmory().getArmoryReference()
			+ "-avatar.jpg";
		return url;
	}

	public CharacterProxy getProxy() {
		return characterProxy;
	}

	public void setPresent(boolean present) {
		this.present = present;
	}

	public boolean isPresent() {
		return present;
	}

	public void join() {
		if (!present) {
			present = true;
			presenter.joinParty(this);
		}
	}

	public void leave() {
		if (present) {
			present = false;
			presenter.leaveParty(this);
		}
	}
}
