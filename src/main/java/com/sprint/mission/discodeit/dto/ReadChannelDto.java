package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ReadChannelDto(
        UUID userId,
        UUID channelId,
        ChannelType channelType,
        List<UUID> joinedUserIds,
        Instant lastMessageTime
) {
}
