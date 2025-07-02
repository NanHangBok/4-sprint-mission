package com.sprint.mission.discodeit.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorResponse(
        String message,
        Instant timestamp
) {
}
