package org.sfnelson.sk.client.request;

public interface RequestFactory extends com.google.gwt.requestfactory.shared.RequestFactory {

    public GaeRequest loginRequest();
    public GroupRequest groupRequest();
    public EventRequest eventRequest();
    public CharacterRequest characterRequest();
    public LootRequest lootRequest();

}
