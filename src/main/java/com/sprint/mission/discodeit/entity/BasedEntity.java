package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class BasedEntity {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private boolean isActive;

    public BasedEntity() {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
    }

    /**
     * Getter
     **/
    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Setter
     **/
    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
