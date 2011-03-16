package org.sfnelson.sk.client.place;

import org.sfnelson.sk.client.request.GroupProxy;
import org.sfnelson.sk.client.request.RequestFactory;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.requestfactory.shared.EntityProxyId;

public class ShowGroup extends Place {

    private final String groupId;

    public static ShowGroup create(RequestFactory factory, GroupProxy group) {
        return new ShowGroup(factory.getHistoryToken(group.stableId()));
    }

    public static EntityProxyId<GroupProxy> id(RequestFactory factory, ShowGroup place) {
        return factory.getProxyId(place.groupId);
    }

    ShowGroup(String groupId) {
        this.groupId = groupId;
    }

    String getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        return new Tokenizer().getToken(this);
    }

    public static class Tokenizer implements PlaceTokenizer<ShowGroup> {

        @Override
        public ShowGroup getPlace(String token) {
            return new ShowGroup(token);
        }

        @Override
        public String getToken(ShowGroup place) {
            return place.groupId;
        }
    }
}
