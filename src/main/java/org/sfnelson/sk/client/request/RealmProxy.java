package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.RealmManager;
import org.sfnelson.sk.server.domain.Realm;

import com.google.gwt.requestfactory.shared.EntityProxy;
import com.google.gwt.requestfactory.shared.ProxyFor;

@ProxyFor(value=Realm.class, locator=RealmManager.class)
public interface RealmProxy extends EntityProxy {
	String getRegion();
	String getServer();
	String getType();
	String getPopulation();
	String getLocale();
}
