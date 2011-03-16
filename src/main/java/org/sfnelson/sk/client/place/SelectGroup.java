package org.sfnelson.sk.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SelectGroup extends Place {

    public SelectGroup() {}

    public static class Tokenizer implements PlaceTokenizer<SelectGroup> {

        @Override
        public SelectGroup getPlace(String token) {
            return new SelectGroup();
        }

        @Override
        public String getToken(SelectGroup place) {
            return "";
        }

    }
}
