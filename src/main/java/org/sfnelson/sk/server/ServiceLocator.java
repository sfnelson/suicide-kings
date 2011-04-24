package org.sfnelson.sk.server;

import org.sfnelson.sk.server.request.EventService;
import org.sfnelson.sk.server.request.GaeUserService;
import org.sfnelson.sk.server.request.GroupService;
import org.sfnelson.sk.server.request.RealmService;

public class ServiceLocator implements com.google.gwt.requestfactory.shared.ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		if (clazz.isAssignableFrom(CharacterManager.class)) {
			System.out.println("[locator] character manager requested");
			return new CharacterManager();
		}
		else if (clazz.isAssignableFrom(EventService.class)) {
			System.out.println("[locator] event manager requested");
			return new EventManager();
		}
		else if (clazz.isAssignableFrom(GaeUserService.class)) {
			System.out.println("[locator] user manager requested");
			return new UserManager();
		}
		else if (clazz.isAssignableFrom(GroupService.class)) {
			System.out.println("[locator] group manager requested");
			return new GroupManager();
		}
		else if (clazz.isAssignableFrom(RealmService.class)) {
			System.out.println("[locator] realm manager requested");
			return new RealmManager();
		}
		else {
			throw new NullPointerException("I don't know how to produce a " + clazz.getSimpleName() + " for you!");
		}
	}

}
