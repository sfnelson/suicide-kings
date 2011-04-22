package org.sfnelson.sk.server.request;

import java.util.List;

import org.sfnelson.sk.server.domain.Realm;

public interface RealmService {

	List<Realm> getRealms(String region);
	void refresh(String region);

}
