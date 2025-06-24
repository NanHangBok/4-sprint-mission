package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageResponseDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {
    public Message toMessage(MessagePostDto postDto) {
        return new Message(postDto.userId(),postDto.channelId(),postDto.content());
    }

    public MessageResponseDto toMessageResponseDto(Message message, List<BinaryContentResponseDto> binaryContentResponseDtos) {
        return new MessageResponseDto(message.getUserId(), message.getChannelId(), message.getContent(), binaryContentResponseDtos);
    }
}
