package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class BasedEntity {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    public BasedEntity() {
        Long time = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.createdAt = time;
        this.updatedAt = time;
    }

    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
