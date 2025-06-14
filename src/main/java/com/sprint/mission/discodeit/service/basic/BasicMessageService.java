package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.ActiveStatus;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;

    public BasicMessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    public void validateActiveMessage(Message message) {
        if (!message.isActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("Message is not active");
    }

    // 메시지 생성
    @Override
    public Message createMessage(String content, User user, Channel channel) {
        Message message = new Message(content,user,channel);
        if (!user.isActive().equals(ActiveStatus.ACTIVE)
                || !channel.isActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");
        message.setActive(ActiveStatus.ACTIVE);
        channel.addMessage(message);
        user.addMessage(message);
        messageRepository.save(message);
        channelRepository.save(channel);
        userRepository.save(user);
        return message;
    }


    // 모든 메시지 확인
    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    // 특정 ID를 가진 메시지 확인
    @Override
    public Message getMessagesById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    // 메시지 내용 수정
    // 현재는 내용 1개만 수정 가능
    @Override
    public void updateMessage(Message message, String content) {
        validateActiveMessage(message);

        Message msg = messageRepository.findById(message.getId());
        User user = userRepository.findById(msg.getUserId());
        Channel channel = channelRepository.findById(msg.getChannelId());

        message.setContent(content);
        message.setUpdatedAt(System.currentTimeMillis());

        user.removeMessage(message);
        channel.removeMessage(message);
        message.addUser(user);
        message.addChannel(channel);

        userRepository.save(user);
        channelRepository.save(channel);
        messageRepository.save(message);
    }

    // 메시지 삭제
    public void removeMessage(Message message) {
        validateActiveMessage(message);

        message.setActive(ActiveStatus.DELETE);
        messageRepository.delete(message);

        User sender = message.getUser();
        Channel channel = message.getChannel();
        sender.removeMessage(message);
        channel.removeMessage(message);
        userRepository.save(sender);
        channelRepository.save(channel);
    }

    // 활성화 된 모든 메시지 확인
    @Override
    public List<Message> getActiveMessages() {
        return messageRepository.findAllActive();
    }

}
