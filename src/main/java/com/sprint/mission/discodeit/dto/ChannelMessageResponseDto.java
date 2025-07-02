package com.sprint.mission.discodeit.dto;

import java.util.List;
import java.util.UUID;

public record ChannelMessageResponseDto(
        UUID channelId,
        List<MessageResponseDto> messages
) {
}
