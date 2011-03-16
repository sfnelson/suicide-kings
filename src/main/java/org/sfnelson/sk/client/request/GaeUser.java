package org.sfnelson.sk.client.request;

import com.google.gwt.requestfactory.shared.ProxyForName;
import com.google.gwt.requestfactory.shared.ValueProxy;

@ProxyForName("com.google.appengine.api.users.User")
public interface GaeUser extends ValueProxy {
    String getUserId();
    String getEmail();
    String getNickname();
}
