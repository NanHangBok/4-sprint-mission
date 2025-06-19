package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.User;

import java.util.UUID;

public record CreateProfileUserDto(
        String name,
        String password,
        String email,
        byte[] binaryContent
) { }
