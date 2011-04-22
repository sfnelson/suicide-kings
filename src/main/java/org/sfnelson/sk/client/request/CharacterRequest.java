package org.sfnelson.sk.client.request;

import java.util.List;

import org.sfnelson.sk.server.ServiceLocator;
import org.sfnelson.sk.server.request.CharacterService;

import com.google.gwt.requestfactory.shared.Request;
import com.google.gwt.requestfactory.shared.RequestContext;
import com.google.gwt.requestfactory.shared.Service;

@Service(value=CharacterService.class, locator=ServiceLocator.class)
public interface CharacterRequest extends RequestContext {
	Request<CharacterProxy> registerCharacter(GroupProxy group, String name);
	Request<List<CharacterProxy>> findCharactersForGroup(GroupProxy group);
}
