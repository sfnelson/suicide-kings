package org.sfnelson.sk.server;

import org.sfnelson.sk.server.request.CharacterService;
import org.sfnelson.sk.server.request.EventService;
import org.sfnelson.sk.server.request.GaeUserService;
import org.sfnelson.sk.server.request.GroupService;
import org.sfnelson.sk.server.request.LootService;
import org.sfnelson.sk.server.request.RealmService;

public class ServiceLocator implements com.google.gwt.requestfactory.shared.ServiceLocator {

	@Override
	public Object getInstance(Class<?> clazz) {
		if (clazz.isAssignableFrom(CharacterService.class)) {
			return new CharacterManager();
		}
		else if (clazz.isAssignableFrom(EventService.class)) {
			return new EventManager();
		}
		else if (clazz.isAssignableFrom(GaeUserService.class)) {
			return new UserManager();
		}
		else if (clazz.isAssignableFrom(GroupService.class)) {
			return new GroupManager();
		}
		else if (clazz.isAssignableFrom(RealmService.class)) {
			return new RealmManager();
		}
		else if (clazz.isAssignableFrom(LootService.class)) {
			return new LootManager();
		}
		else {
			throw new NullPointerException("I don't know how to produce a " + clazz.getSimpleName() + " for you!");
		}
	}

}
