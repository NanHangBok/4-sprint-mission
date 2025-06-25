package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageResponseDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public MessageResponseDto createMessage(MessagePostDto messagePostDto);
    public List<MessageResponseDto> findallByChannelId(UUID channelId);
    public MessageResponseDto getMessagesById(UUID messageId);
    public MessageResponseDto updateMessage(MessageUpdateDto messageUpdateDto);;
    public void removeMessage(UUID messageId);
    public List<MessageResponseDto> getActiveMessages();
    public List<MessageResponseDto> findAll();
}
