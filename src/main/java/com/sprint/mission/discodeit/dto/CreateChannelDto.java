package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record CreateChannelDto(
        UUID hostUserId,
        String channelName,
        String description
) {
}
