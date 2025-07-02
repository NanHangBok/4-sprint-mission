package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ChannelResponseDto(
        UUID id,
        UUID userId,
        UUID recipientId,
        String channelName,
        String description,
        ChannelType channelType,
        List<UUID> joinedUserIds,
        Instant lastMessageTime
) {
}
