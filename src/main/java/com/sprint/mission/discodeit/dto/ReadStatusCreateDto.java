package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record ReadStatusCreateDto(
        UUID channelId,
        UUID userId
) {
}
