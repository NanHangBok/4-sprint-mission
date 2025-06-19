package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record CreateReadStatusDto(
        UUID userId,
        UUID channelId
) {
}
