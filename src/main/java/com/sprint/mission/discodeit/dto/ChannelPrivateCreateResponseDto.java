package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.util.UUID;

public record ChannelPrivateCreateResponseDto(
        UUID id,
        ChannelType channelType,
        UUID user1Id,
        UUID user2Id
) {
}
