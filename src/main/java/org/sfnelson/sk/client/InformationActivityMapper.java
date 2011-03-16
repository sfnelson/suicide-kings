package org.sfnelson.sk.client;

import org.sfnelson.sk.client.activity.ShowEvents;
import org.sfnelson.sk.client.activity.ShowGroupDetails;
import org.sfnelson.sk.client.place.SelectGroup;
import org.sfnelson.sk.client.place.ShowGroup;
import org.sfnelson.sk.client.request.GroupProxy;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.requestfactory.shared.EntityProxyId;

public class InformationActivityMapper implements ActivityMapper {

    private final Factory factory;

    public InformationActivityMapper(Factory factory) {
        this.factory = factory;
    }

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof ShowGroup) {
            ShowGroup p = (ShowGroup) place;
            EntityProxyId<GroupProxy> groupId = ShowGroup.id(factory.getRequestFactory(), p);

            return new ShowEvents(factory, groupId);
        }
        else if (place instanceof SelectGroup) {
            System.out.println("showing group details panel");
            return new ShowGroupDetails(factory);
        }
        else {
            return null;
        }
    }

}
