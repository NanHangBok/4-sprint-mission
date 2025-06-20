package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record ChannelPostDto(
        UUID hostUserId,
        String channelName,
        String description
) {
}
