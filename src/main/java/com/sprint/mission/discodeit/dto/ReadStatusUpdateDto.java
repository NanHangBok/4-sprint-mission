package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public record ReadStatusUpdateDto(
        UUID id,
        UUID channelId,
        UUID userId,
        Instant latestTime
) {
}
