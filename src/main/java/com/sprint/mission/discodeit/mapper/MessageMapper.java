package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public Message toMessageForCreate(MessagePostDto postDto) {
        return new Message(postDto.userId(),postDto.channelId(),postDto.content());
    }
    public Message toMessageForUpdate(MessageUpdateDto updateDto) {
        return new Message(updateDto.id(),updateDto.id(),updateDto.content());
    }
}
