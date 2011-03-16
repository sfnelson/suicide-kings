package org.sfnelson.sk.server.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Server {

    private String realmName;
    private String serverName;

    public Server() {}

    public Server(String realm, String server) {
        this.realmName = realm;
        this.serverName = server;
    }

    public String getRealm() {
        return realmName;
    }

    public void setRealm(String realm) {
        this.realmName = realm;
    }

    public String getServer() {
        return serverName;
    }

    public void setServer(String server) {
        this.serverName = server;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((realmName == null) ? 0 : realmName.hashCode());
        result = prime * result + ((serverName == null) ? 0 : serverName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Server other = (Server) obj;
        if (realmName == null) {
            if (other.realmName != null)
                return false;
        } else if (!realmName.equals(other.realmName))
            return false;
        if (serverName == null) {
            if (other.serverName != null)
                return false;
        } else if (!serverName.equals(other.serverName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return realmName + "-" + serverName;
    }
}
