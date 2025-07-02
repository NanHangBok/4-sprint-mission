package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.BinaryContentType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BinaryContentMapper {
    public BinaryContent toBinaryContent(BinaryContentPostDto postDto) {
        return new BinaryContent(postDto.content(),postDto.contentType());
    }

    public BinaryContentResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
        return new BinaryContentResponseDto(binaryContent.getId(),binaryContent.getContentTypeEnum(), binaryContent.getBytes());
    }

    public BinaryContentPostDto ofBinaryContentPostDto(BinaryContentType binaryContentType, MultipartFile file) {
        return new BinaryContentPostDto(binaryContentType,file);
    }
}
