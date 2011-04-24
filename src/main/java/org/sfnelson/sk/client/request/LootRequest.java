package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.ServiceLocator;
import org.sfnelson.sk.server.request.LootService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=LootService.class, locator=ServiceLocator.class)
public interface LootRequest extends RequestContext {

	Request<LootProxy> findLoot(Long id);

}
