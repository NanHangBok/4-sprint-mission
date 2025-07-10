package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record BinaryContentResponseDto(
        UUID id,
        Long size,
        String finename,
        String contentType,
        byte[] bytes
) {
}
