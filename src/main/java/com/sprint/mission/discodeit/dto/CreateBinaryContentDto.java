package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record CreateBinaryContentDto(
        UUID userId,
        UUID messageId,
        byte[] content
) {
}
