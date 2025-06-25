package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.BinaryContentMapper;
import com.sprint.mission.discodeit.mapper.MessageMapper;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final BinaryContentMapper binaryContentMapper;
    private final MessageMapper messageMapper;

    // 메시지 생성
    @Override
    public MessageResponseDto createMessage(MessagePostDto messagePostDto) {
        Message message = new Message(messagePostDto.userId(), messagePostDto.channelId(), messagePostDto.content());
        Channel channel = channelRepository.findById(messagePostDto.channelId());
        User user = userRepository.findById(messagePostDto.userId());

        if (!user.getActive().equals(ActiveStatus.ACTIVE)
                || !channel.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("User is not active");

        if (messagePostDto.attachments() != null && !messagePostDto.attachments().isEmpty()) {
            messagePostDto.attachments().stream()
                    .filter(binaryContent -> binaryContent != null)
                    .forEach(binaryContent -> {
                        BinaryContent biContent = binaryContentMapper.toBinaryContent(binaryContent);
                        message.getAttachmentIds().add(biContent.getId());
                        binaryContentRepository.save(biContent);
                    });
        }
        message.setActive(ActiveStatus.ACTIVE);
        messageRepository.save(message);

        channel.addMessageToChannel(message);
        channelRepository.save(channel);
        MessageResponseDto messageResponseDto = messageMapper.toMessageResponseDto(message);
        return messageResponseDto;
    }

    // 모든 메시지 확인
    @Override
    public List<MessageResponseDto> findallByChannelId(UUID channelId) {
        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        messageRepository.findAll().stream()
                .filter(message -> message.getChannelId().equals(channelId))
                .forEach(message -> messageResponseDtos.add(messageMapper.toMessageResponseDto(message)));

        return  messageResponseDtos;
    }

    // 특정 ID를 가진 메시지 확인
    @Override
    public MessageResponseDto getMessagesById(UUID messageId) {
        return messageMapper.toMessageResponseDto(messageRepository.findById(messageId));
    }

    // 메시지 내용 수정
    // 현재는 내용 1개만 수정 가능
    @Override
    public MessageResponseDto updateMessage(MessageUpdateDto messageUpdateDto) {
        Message findMessage = messageRepository.findById(messageUpdateDto.id());
        validateActiveMessage(findMessage);

        Optional.ofNullable(messageUpdateDto.content()).ifPresent(findMessage::setContent);
        findMessage.setUpdatedAt(Instant.now());

        messageRepository.save(findMessage);

        return messageMapper.toMessageResponseDto(findMessage);
    }

    // 메시지 삭제
    public void removeMessage(UUID messageId) {
        Message findMessage = messageRepository.findById(messageId);
        validateActiveMessage(findMessage);

        findMessage.setActive(ActiveStatus.DELETE);
        messageRepository.delete(findMessage);
        if (findMessage.getAttachmentIds() != null && !findMessage.getAttachmentIds().isEmpty()) {
            findMessage.getAttachmentIds().stream()
                    .forEach(id -> {
                        binaryContentRepository.delete(id);
                    });
        }
        Channel channel =  channelRepository.findById(findMessage.getChannelId());
        channel.removeMessageFromChannel(findMessage);
        channelRepository.save(channel);
    }

    // 활성화 된 모든 메시지 확인
    @Override
    public List<MessageResponseDto> getActiveMessages() {
        List<MessageResponseDto> activeMessageResponseDtos = new ArrayList<>();
        messageRepository.findAllActive().stream()
                .forEach(message -> activeMessageResponseDtos.add(messageMapper.toMessageResponseDto(message)));
        return activeMessageResponseDtos;
    }

    @Override
    public List<MessageResponseDto> findAll() {
        List<MessageResponseDto> messageResponseDtos = new ArrayList<>();
        messageRepository.findAll().stream()
                .forEach(message -> messageResponseDtos.add(messageMapper.toMessageResponseDto(message)));
        return messageResponseDtos;
    }

    private void validateActiveMessage(Message message) {
        if (!message.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalArgumentException("Message is not active");
    }
}
