package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.domain.Server;

import com.google.gwt.requestfactory.shared.ProxyFor;
import com.google.gwt.requestfactory.shared.ValueProxy;

@ProxyFor(Server.class)
public interface ServerProxy extends ValueProxy {
    public String getRealm();
    public void setRealm(String realm);
    public String getServer();
    public void setServer(String server);
}
