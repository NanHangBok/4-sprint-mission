package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public record UserResponseDto(
        String nickname,
        String email,
        UUID profile,
        UserStatus userStatus
) {
}
