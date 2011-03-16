package org.sfnelson.sk.server;

import com.google.appengine.api.users.User;

public interface UserService {

    String createLoginURL(String destination);
    String createLogoutURL(String destination);
    User getCurrentUser();

}
