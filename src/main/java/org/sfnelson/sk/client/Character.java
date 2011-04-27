package org.sfnelson.sk.client;

import org.sfnelson.sk.client.place.Group;
import org.sfnelson.sk.client.request.CharacterProxy;
import org.sfnelson.sk.client.request.EventRequest;
import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.RequestFactory;

import com.google.gwt.user.client.ui.Anchor;

public class Character {

	private final RequestFactory rf;

	private final CharacterProxy characterProxy;
	private final GroupProxy groupProxy;
	private final Group group;

	private boolean present = false;

	public Character(RequestFactory rf, CharacterProxy characterProxy, GroupProxy groupProxy, Group group) {
		this.rf = rf;
		this.characterProxy = characterProxy;
		this.groupProxy = groupProxy;
		this.group = group;
	}

	public String getName() {
		return characterProxy.getName();
	}

	public String getAvatar() {
		String url = "http://" + group.getRealm().getRegion() + ".battle.net/static-render/"
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
			EventRequest rq = rf.eventRequest();
			rq.joinParty(groupProxy, characterProxy);
			rq.fire();
		}
	}

	public void leave() {
		if (present) {
			present = false;
			EventRequest rq = rf.eventRequest();
			rq.leaveParty(groupProxy, characterProxy);
			rq.fire();
		}
	}

	public String getHref() {
		String href = "http://" + group.getRealm().getRegion() + ".battle.net/wow/en/character"
		+ "/" + group.getRealm().getServer()
		+ "/" + getName().toLowerCase() + "/";

		Anchor link = new Anchor();
		link.setHref(href);
		link.setText(getName());
		return link.toString();
	}
}
