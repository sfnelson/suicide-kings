package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.ServiceLocator;
import org.sfnelson.sk.server.request.GaeUserService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=GaeUserService.class, locator=ServiceLocator.class)
public interface GaeUserRequest extends RequestContext {
	Request<GaeUserProxy> getCurrentUser();
	Request<String> createLoginURL(String currentURL);
	Request<String> createLogoutURL(String currentURL);
}
