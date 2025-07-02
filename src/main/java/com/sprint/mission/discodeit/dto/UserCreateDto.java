package com.sprint.mission.discodeit.dto;

import org.springframework.web.multipart.MultipartFile;

public record UserCreateDto(
        String name,
        String nickname,
        String password,
        String email,
        BinaryContentPostDto binaryContentPostDto
) {
}
