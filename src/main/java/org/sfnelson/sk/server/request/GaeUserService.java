package org.sfnelson.sk.server.request;

import com.google.appengine.api.users.User;

public interface GaeUserService {

	String createLoginURL(String destination);
	String createLogoutURL(String destination);
	User getCurrentUser();

}
