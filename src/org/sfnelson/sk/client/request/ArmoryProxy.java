package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.domain.ArmoryReference;

import com.google.gwt.requestfactory.shared.ProxyFor;
import com.google.gwt.requestfactory.shared.ValueProxy;

@ProxyFor(ArmoryReference.class)
public interface ArmoryProxy extends ValueProxy {
    Long getArmoryHash();
    Long getArmoryReference();
}
