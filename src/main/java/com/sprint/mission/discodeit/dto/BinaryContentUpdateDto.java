package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContentType;

import java.util.UUID;

public record BinaryContentUpdateDto(
        UUID userId,
        BinaryContentPostDto binaryContentPostDto
) {
}
