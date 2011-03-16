package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.LootService;
import org.sfnelson.sk.server.domain.Loot;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Loot.class, locator=LootService.class)
public interface LootProxy extends EntityProxy {

    Long getReference();
    String getName();
    EntityProxyId<LootProxy> stableId();

}
