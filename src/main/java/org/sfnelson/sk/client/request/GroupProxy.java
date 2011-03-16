package org.sfnelson.sk.client.request;

import java.util.Set;

import org.sfnelson.sk.server.GroupService;
import org.sfnelson.sk.server.domain.Group;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Group.class, locator=GroupService.class)
public interface GroupProxy extends EntityProxy {

    ServerProxy getServer();
    String getName();
    Set<String> getOwners();

    EntityProxyId<GroupProxy> stableId();
}
