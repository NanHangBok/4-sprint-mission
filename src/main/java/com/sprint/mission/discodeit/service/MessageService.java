package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    public Message createMessage(String content, User user, Channel channel);
    public List<Message> getMessages();
    public Message getMessagesById(UUID messageId);
    public void updateMessage(Message message, String content);;
    public void removeMessage(Message messgae);
    public List<Message> getActiveMessages();
}
