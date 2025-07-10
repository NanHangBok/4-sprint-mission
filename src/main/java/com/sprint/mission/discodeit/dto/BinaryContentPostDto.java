package com.sprint.mission.discodeit.dto;

public record BinaryContentPostDto(
        String fileName,
        String contentType,
        byte[] content
) {
}
