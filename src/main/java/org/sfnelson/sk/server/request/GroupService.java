package org.sfnelson.sk.server.request;

import java.util.List;

import org.sfnelson.sk.server.domain.Group;

public interface GroupService {
	public Group createGroup(String region, String server, String name);
	public void deleteGroup(Group group);
	public Group findGroup(String region, String server, String name);
	public List<Group> findGroups(String region, String server);
}
