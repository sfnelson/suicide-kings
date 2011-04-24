package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.LootManager;
import org.sfnelson.sk.server.domain.Loot;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Loot.class, locator=LootManager.class)
public interface LootProxy extends EntityProxy {

	Long getId();

	Long getReference();
	String getName();

	@Override
	EntityProxyId<LootProxy> stableId();

}
