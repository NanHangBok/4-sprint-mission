package com.sprint.mission.discodeit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UserStatus extends BasedEntity {
    private UUID userId;
    private Instant lastActiveAt;
    public boolean isRecentlyActive() {
        if (lastActiveAt.isAfter(Instant.now().minusSeconds(300))) return true;

        return false;
    }
}
