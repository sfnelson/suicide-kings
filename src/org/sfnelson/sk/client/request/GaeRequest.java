package org.sfnelson.sk.client.request;

import org.sfnelson.sk.server.UserLocator;
import org.sfnelson.sk.server.UserService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=UserService.class, locator=UserLocator.class)
public interface GaeRequest extends RequestContext {
    Request<GaeUser> getCurrentUser();
    Request<String> createLoginURL(String currentURL);
    Request<String> createLogoutURL(String currentURL);
}
