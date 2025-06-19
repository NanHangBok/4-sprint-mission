package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public record UpdateReadStatusDto(
        UUID id,
        Instant latestTime
) {
}
