package org.sfnelson.sk.client.request;

import java.util.List;

import org.sfnelson.sk.server.GroupService;
import org.sfnelson.sk.server.ServiceLocator;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=GroupService.class, locator=ServiceLocator.class)
public interface GroupRequest extends RequestContext {

    Request<List<GroupProxy>> findGroups();
    Request<Void> createGroup(String name, String realm, String server);
    Request<Void> deleteGroup(GroupProxy group);

}
