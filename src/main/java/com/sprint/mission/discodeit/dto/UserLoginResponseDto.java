package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.PresenceStatus;

import java.util.UUID;

public record UserLoginResponseDto(
        UUID id,
        UUID profileId,
        String username,
        String email,
        PresenceStatus presenceStatus
) {
}
