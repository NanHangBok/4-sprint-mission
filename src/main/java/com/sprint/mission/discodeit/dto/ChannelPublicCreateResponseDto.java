package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.UUID;

public record ChannelPublicCreateResponseDto(
        UUID id,
        ChannelType channelType,
        String name,
        String description
) {
}
