package org.sfnelson.sk.client;

import org.sfnelson.sk.client.place.SelectRealm;
import org.sfnelson.sk.client.place.ShowGroup;
import org.sfnelson.sk.client.place.Login;
import org.sfnelson.sk.client.place.SelectGroup;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({
    Login.Tokenizer.class,
    SelectRealm.Tokenizer.class,
    SelectGroup.Tokenizer.class,
    ShowGroup.Tokenizer.class
})
public interface HistoryMapper extends PlaceHistoryMapper {

}
