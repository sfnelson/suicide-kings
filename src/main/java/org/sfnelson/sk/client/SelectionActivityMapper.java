package org.sfnelson.sk.client;

import org.sfnelson.sk.client.activity.Authenticate;
import org.sfnelson.sk.client.activity.SelectGroup;
import org.sfnelson.sk.client.activity.ShowLadder;
import org.sfnelson.sk.client.place.Login;
import org.sfnelson.sk.client.place.ShowGroup;
import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;
import com.google.gwt.requestfactory.shared.EntityProxyId;

public class SelectionActivityMapper implements com.google.gwt.activity.shared.ActivityMapper {

    private final Factory factory;

    public SelectionActivityMapper(Factory factory) {
        this.factory = factory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof Login) {
            return new Authenticate(factory);
        }
        if (place instanceof org.sfnelson.sk.client.place.SelectGroup) {
            return new SelectGroup(factory);
        }
        if (place instanceof ShowGroup) {
            ShowGroup p = (ShowGroup) place;
            EntityProxyId<GroupProxy> groupId = ShowGroup.id(factory.getRequestFactory(), p);

            return new ShowLadder(factory, groupId);
        }

        return null;
    }

}
