package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UpdateMessageDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    public Message createMessage(String content, User user, Channel channel);
    public List<Message> findallByChannelId(UUID channelId);
    public Message getMessagesById(UUID messageId);
    public void updateMessage(UpdateMessageDto updateMessageDto);;
    public void removeMessage(Message messgae);
    public List<Message> getActiveMessages();
}
