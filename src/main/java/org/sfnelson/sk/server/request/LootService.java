package org.sfnelson.sk.server.request;

import org.sfnelson.sk.server.domain.Loot;

public interface LootService {
	Loot findLoot(Long id);
}
