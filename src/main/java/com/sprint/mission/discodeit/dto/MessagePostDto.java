package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public record MessagePostDto(
        UUID userId,
        UUID channelId,
        String content,
        List<BinaryContentType> binaryContentTypes,
        List<MultipartFile> attachments
) {
}
