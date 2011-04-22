package org.sfnelson.sk.client.request;

import java.util.List;

import org.sfnelson.sk.server.ServiceLocator;
import org.sfnelson.sk.server.request.RealmService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=RealmService.class, locator=ServiceLocator.class)
public interface RealmRequest extends RequestContext {

	Request<List<RealmProxy>> getRealms(String region);
	Request<Void> refresh(String region);

}
