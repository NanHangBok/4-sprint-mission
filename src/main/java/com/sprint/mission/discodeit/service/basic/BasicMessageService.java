package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentPostDto;
import com.sprint.mission.discodeit.dto.MessagePostDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;

    public void validateActiveMessage(Message message) {
        if (!message.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("Message is not active");
    }

    // 메시지 생성
    @Override
    public Message createMessage(MessagePostDto messagePostDto) {
        Message message = new Message(messagePostDto.userId(), messagePostDto.channelId(), messagePostDto.content());
        if (messagePostDto.attachments() != null && !messagePostDto.attachments().isEmpty()) {
            messagePostDto.attachments().stream()
                    .filter(binaryContent -> binaryContent != null)
                    .forEach(binaryContent -> {
                        BinaryContent biContent = binaryContentMapper.toBinaryContent(binaryContent);
                        message.getAttachmentIds().add(biContent.getId());
                        binaryContentRepository.save(biContent);
                    });
        }
//        if (!user.getActive().equals(ActiveStatus.ACTIVE)
//                || !channel.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");
        message.setActive(ActiveStatus.ACTIVE);

        Channel channel = channelRepository.findById(messagePostDto.channelId());
        User user = userRepository.findById(messagePostDto.userId());
        channel.addMessage(message);
        user.addMessage(message);
        messageRepository.save(message);
        channelRepository.save(channel);
        userRepository.save(user);
        return message;
    }


    // 모든 메시지 확인
    @Override
    public List<Message> findallByChannelId(UUID channelId) {
        return messageRepository.findAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .collect(Collectors.toList());
    }

    // 특정 ID를 가진 메시지 확인
    @Override
    public Message getMessagesById(UUID messageId) {
        return messageRepository.findById(messageId);
    }

    // 메시지 내용 수정
    // 현재는 내용 1개만 수정 가능
    @Override
    public void updateMessage(MessageUpdateDto messageUpdateDto) {
        Message message = messageRepository.findById(messageUpdateDto.id());
        validateActiveMessage(message);

        User user = userRepository.findById(message.getUserId());
        Channel channel = channelRepository.findById(message.getChannelId());

        message.setContent(messageUpdateDto.content());
        message.setUpdatedAt(Instant.now());

        user.removeMessage(message);
        channel.removeMessage(message.getId());
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
        if (message.getAttachmentIds() != null && !message.getAttachmentIds().isEmpty()) {
            message.getAttachmentIds().stream()
                    .forEach(id -> {
                        binaryContentRepository.delete(id);
                    });
        }
        User sender = userRepository.findById(message.getUserId());
        Channel channel = channelRepository.findById(message.getChannelId());
        sender.removeMessage(message);
        channel.removeMessage(message.getId());
        userRepository.save(sender);
        channelRepository.save(channel);
    }

    // 활성화 된 모든 메시지 확인
    @Override
    public List<Message> getActiveMessages() {
        return messageRepository.findAllActive();
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
