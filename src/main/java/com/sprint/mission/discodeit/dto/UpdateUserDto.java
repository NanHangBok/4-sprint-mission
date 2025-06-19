package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.PresenceStatus;

import java.util.UUID;

public record UpdateUserDto(
        UUID id,
        String name,
        String password,
        PresenceStatus presenceStatus,
        byte[] binaryContent
) {
}
