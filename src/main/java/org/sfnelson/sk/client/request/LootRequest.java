package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.LootService;
import org.sfnelson.sk.server.ServiceLocator;

import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=LootService.class, locator=ServiceLocator.class)
public interface LootRequest extends RequestContext {

}
