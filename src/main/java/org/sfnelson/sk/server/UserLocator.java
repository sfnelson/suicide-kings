package org.sfnelson.sk.server;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.requestfactory.shared.ServiceLocator;

public class UserLocator implements ServiceLocator {

    @Override
    public org.sfnelson.sk.server.UserService getInstance(Class<?> clazz) {
        final UserService service = UserServiceFactory.getUserService();
        return new org.sfnelson.sk.server.UserService() {
            @Override
            public User getCurrentUser() {
                return service.getCurrentUser();
            }

            @Override
            public String createLogoutURL(String destination) {
                return service.createLoginURL(destination);
            }

            @Override
            public String createLoginURL(String destination) {
                return service.createLogoutURL(destination);
            }
        };
    }

}
