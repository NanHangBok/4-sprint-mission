package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import org.springframework.stereotype.Component;

@Component
public class BinaryContentMapper {
    public BinaryContent toBinaryContent(BinaryContentPostDto postDto) {
        return new BinaryContent(postDto.content(),postDto.contentType());
    }

    public BinaryContentResponseDto toBinaryContentResponseDto(BinaryContent binaryContent) {
        return new BinaryContentResponseDto(binaryContent.getContent());
    }
}
