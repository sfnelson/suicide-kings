package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.CharacterService;
import org.sfnelson.sk.server.domain.Character;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Character.class, locator=CharacterService.class)
public interface CharacterProxy extends EntityProxy {

    String getName();
    void setName(String name);
    Long getSeed();
    ArmoryProxy getArmory();

    EntityProxyId<CharacterProxy> stableId();
}
