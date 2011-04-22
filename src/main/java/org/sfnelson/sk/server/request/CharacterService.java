package org.sfnelson.sk.server.request;

import java.util.List;

import org.sfnelson.sk.server.domain.Character;
import org.sfnelson.sk.server.domain.Group;

public interface CharacterService {
	Character registerCharacter(Group group, String name);
	List<Character> findCharactersForGroup(Group group);
}
