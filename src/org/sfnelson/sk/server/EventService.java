package org.sfnelson.sk.server;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.sfnelson.sk.server.domain.Event;

import com.google.gwt.requestfactory.shared.Locator;

public class EventService extends Locator<Event, Long> {

    @SuppressWarnings("unchecked")
    public List<Event> getEvents(Date after) {
        if (after == null) {
            after = new Date(0);
        }

        EntityManager em = EMF.get().createEntityManager();
        try {
            Query q = em.createQuery("select from " + Event.class.getName() + " where date > :date");
            q.setParameter("date", after);
            return (List<Event>) q.getResultList();
        }
        catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
        finally {
            em.close();
        }
    }

    @Override
    public Event create(Class<? extends Event> clazz) {
        return new Event();
    }

    @Override
    public Event find(Class<? extends Event> clazz, Long id) {
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
    public Class<Event> getDomainType() {
        return Event.class;
    }

    @Override
    public Long getId(Event event) {
        return event.getId();
    }

    @Override
    public Class<Long> getIdType() {
        return Long.class;
    }

    @Override
    public Integer getVersion(Event event) {
        return event.getVersion();
    }

}
