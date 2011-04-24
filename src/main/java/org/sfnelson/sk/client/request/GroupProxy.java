package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.GroupManager;
import org.sfnelson.sk.server.domain.Group;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Group.class, locator=GroupManager.class)
public interface GroupProxy extends EntityProxy {

	String getName();

	@Override
	EntityProxyId<GroupProxy> stableId();
}
