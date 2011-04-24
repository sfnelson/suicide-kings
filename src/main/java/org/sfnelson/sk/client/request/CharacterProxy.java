package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.CharacterManager;
import org.sfnelson.sk.server.domain.Character;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Character.class, locator=CharacterManager.class)
public interface CharacterProxy extends EntityProxy {

	Long getId();

	String getName();
	void setName(String name);
	Long getSeed();
	ArmoryProxy getArmory();

	@Override
	EntityProxyId<CharacterProxy> stableId();
}
