package com.sprint.mission.discodeit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(type = "object", description = "로그인 정보")
public record LoginRequest(
        String username,
        String password
) {
}
