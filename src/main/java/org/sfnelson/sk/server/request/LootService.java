package org.sfnelson.sk.server.request;

import java.util.List;

import org.sfnelson.sk.server.domain.Loot;

public interface LootService {
	Loot findLootById(Long id);
	Loot findLootByReference(Long id);
	List<Loot> findLootsById(List<Long> ids);
}
