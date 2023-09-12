package com.maximus.chatclientjavafx.displaymanager;

public class CurrentEntity {

    private EEntityType type;
    private Long uniqueId;


    public CurrentEntity(EEntityType type, Long uniqueId) {
        this.type = type;
        this.uniqueId = uniqueId;
    }

    public EEntityType getType() {
        return type;
    }

    public void setType(EEntityType type) {
        this.type = type;
    }

    public Long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
