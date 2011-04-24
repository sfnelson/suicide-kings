package org.sfnelson.sk.client.request;

import java.util.List;

import org.sfnelson.sk.server.ServiceLocator;
import org.sfnelson.sk.server.request.GroupService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=GroupService.class, locator=ServiceLocator.class)
public interface GroupRequest extends RequestContext {
	Request<List<GroupProxy>> findGroups(String region, String server);
	Request<GroupProxy> createGroup(String region, String server, String name);
	Request<GroupProxy> findGroup(String region, String server, String name);
	Request<Void> deleteGroup(GroupProxy group);
}
