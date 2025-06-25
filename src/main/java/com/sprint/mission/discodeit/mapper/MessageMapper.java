package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toMessage(MessagePostDto postDto) {
        return new Message(postDto.userId(),postDto.channelId(),postDto.content());
    }

    public MessageResponseDto toMessageResponseDto(Message message) {
        return new MessageResponseDto(message.getId(), message.getUserId(), message.getChannelId(), message.getContent(), message.getAttachmentIds());
    }
}
