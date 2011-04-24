package org.sfnelson.sk.server.request;

import java.util.List;

import org.sfnelson.sk.server.domain.Character;

public interface CharacterService {
	Character registerCharacter(String region, String server, String group, String name);
	List<Character> findCharactersForGroup(String region, String server, String group);
	List<Character> findCharacters(String region, String server);
	Character findCharacter(Long id);
}