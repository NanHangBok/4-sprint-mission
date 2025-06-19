package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.UUID;

public record ReadUserDto(
        String name,
        String email,
        UUID profile,
        UserStatus userStatus
) {
}
