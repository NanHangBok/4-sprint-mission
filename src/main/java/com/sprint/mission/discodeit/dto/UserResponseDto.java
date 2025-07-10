package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String username,
        String email,
        UUID profile,
        UserStatusResponseDto userStatus
) {
}
