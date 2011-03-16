package org.sfnelson.sk.server;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class EMF {
	private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("transactions-optional");

    private EMF() {}

    static EntityManagerFactory get() {
        return emf;
    }
}
