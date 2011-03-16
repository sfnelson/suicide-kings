package org.sfnelson.sk.server.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ArmoryReference {

    private Long armoryHash;
    private Long armoryReference;

    public ArmoryReference() {}

    public ArmoryReference(Long armoryHash, Long armoryReference) {
        this.armoryHash = armoryHash;
        this.armoryReference = armoryReference;
    }

    public void setArmoryHash(Long armoryHash) {
        this.armoryHash = armoryHash;
    }

    public Long getArmoryHash() {
        return armoryHash;
    }

    public void setArmoryReference(Long armoryReference) {
        this.armoryReference = armoryReference;
    }

    public Long getArmoryReference() {
        return armoryReference;
    }
}
