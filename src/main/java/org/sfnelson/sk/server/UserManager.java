package org.sfnelson.sk.server;

import org.sfnelson.sk.server.request.GaeUserService;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UserManager implements GaeUserService {

	private final UserService service;

	public UserManager() {
		service = UserServiceFactory.getUserService();
	}

	@Override
	public String createLoginURL(String destination) {
		return service.createLoginURL(destination);
	}

	@Override
	public String createLogoutURL(String destination) {
		return service.createLogoutURL(destination);
	}

	@Override
	public User getCurrentUser() {
		return service.getCurrentUser();
	}

}
