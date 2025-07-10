package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(type = "ojbect", description = "Private Channel 생성 정보")
public record ChannelPrivateCreateResponseDto(
        UUID id,
        ChannelType channelType,
        List<UUID> ids
) {
}
