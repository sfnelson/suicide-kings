package org.sfnelson.sk.client.request;

import java.util.Date;

import org.sfnelson.sk.server.EventManager;
import org.sfnelson.sk.server.domain.Event;
import org.sfnelson.sk.shared.EventType;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.EntityProxyId;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Event.class, locator=EventManager.class)
public interface EventProxy extends EntityProxy {

    Date getDate();
    EventType getType();
    Long getGroupId();
    Long getCharacterId();
    Long getLootId();
    String getInfo();

    EntityProxyId<EventProxy> stableId();
}
