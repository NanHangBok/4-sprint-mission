package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper {
    public Message toMessage(MessageCreateDto postDto) {
        return new Message(postDto.userId(),postDto.channelId(),postDto.content());
    }

    public MessageResponseDto toMessageResponseDto(Message message) {
        return new MessageResponseDto(message.getId(), message.getUserId(), message.getChannelId(), message.getContent(), message.getAttachmentIds(), message.getCreatedAt(), message.getUpdatedAt());
    }

    public MessageCreateDto ofMessageCreateDto(MessagePostDto messagePostDto, List<BinaryContentPostDto> binaryContentPostDtos) {
        return new MessageCreateDto(messagePostDto.userId(), messagePostDto.channelId(), messagePostDto.content(), binaryContentPostDtos);
    }
}
