package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.PresenceStatus;

import java.util.UUID;

public record UserLoginResponseDto(
        UUID id,
        UUID profileId,
        String nickname,
        String email,
        PresenceStatus presenceStatus
) {
}
