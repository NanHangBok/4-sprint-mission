package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public record ChannelPrivatePostDto(
        UUID user1,
        UUID user2
) {
}
