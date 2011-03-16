package org.sfnelson.sk.server;

public class ServiceLocator implements com.google.gwt.requestfactory.shared.ServiceLocator {

    @Override
    public Object getInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
