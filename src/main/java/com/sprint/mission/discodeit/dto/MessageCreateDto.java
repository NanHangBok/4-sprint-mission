package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(type = "object", description = "Message 생성 정보")
public record MessageCreateDto(
        UUID userId,
        UUID channelId,
        String content
) {
}
