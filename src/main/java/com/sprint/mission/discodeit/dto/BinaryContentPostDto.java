package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record BinaryContentPostDto(
        BinaryContentType contentType,
        MultipartFile content
) {
}
