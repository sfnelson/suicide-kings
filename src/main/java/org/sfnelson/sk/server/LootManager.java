package org.sfnelson.sk.server;

import javax.persistence.EntityManager;

import org.sfnelson.sk.server.domain.Loot;

import com.google.gwt.requestfactory.shared.Locator;

public class LootManager extends Locator<Loot, Long> {

    @Override
    public Loot create(Class<? extends Loot> clazz) {
        return new Loot();
    }

    @Override
    public Loot find(Class<? extends Loot> clazz, Long id) {
        if (id == null) {
            return null;
        }

        EntityManager em = EMF.get().createEntityManager();
        try {
            return em.find(clazz, id);
        }
        finally {
            em.close();
        }
    }

    @Override
    public Class<Loot> getDomainType() {
        return Loot.class;
    }

    @Override
    public Long getId(Loot loot) {
        return loot.getId();
    }

    @Override
    public Class<Long> getIdType() {
        return Long.class;
    }

    @Override
    public Integer getVersion(Loot loot) {
        return loot.getVersion();
    }

}
