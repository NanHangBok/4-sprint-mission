package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public Message createMessage(MessagePostDto messagePostDto);
    public List<Message> findallByChannelId(UUID channelId);
    public Message getMessagesById(UUID messageId);
    public void updateMessage(MessageUpdateDto messageUpdateDto);;
    public void removeMessage(Message messgae);
    public List<Message> getActiveMessages();
    public List<Message> findAll();
}
