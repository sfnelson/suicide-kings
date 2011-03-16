package org.sfnelson.sk.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class Login extends Place {

    public static class Tokenizer implements PlaceTokenizer<Login> {

        @Override
        public Login getPlace(String token) {
            return new Login();
        }

        @Override
        public String getToken(Login place) {
            return "";
        }

    }
}
