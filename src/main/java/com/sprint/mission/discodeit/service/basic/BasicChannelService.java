package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final ChannelMapper channelMapper;
    private final ReadStatusMapper readStatusMapper;
    private final MessageService messageService;
    private final UserRepository userRepository;

    @Override
    public ChannelPublicCreateResponseDto createPublicChannel(ChannelPostDto channelPostDto) {
        validateUser(channelPostDto.hostUserId());
        Channel channel = channelMapper.toPublicChannel(channelPostDto);
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);
        ReadStatus readStatus = new ReadStatus(channelPostDto.hostUserId(),channel.getId(),Instant.now());
        readStatusRepository.save(readStatus);
        return channelMapper.toChannelPublicCreateResponseDto(channel);
    }

    @Override
    public ChannelPrivateCreateResponseDto createPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto) {
        validateUser(channelPrivatePostDto.host());
        validateUser(channelPrivatePostDto.recipient());

        Channel channel = channelMapper.toPrivateChannel(channelPrivatePostDto);
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);

        ReadStatus readStatus = new ReadStatus(channelPrivatePostDto.host(),channel.getId(),Instant.now());
        ReadStatus readStatus2 = new ReadStatus(channelPrivatePostDto.recipient(),channel.getId(),Instant.now());
        readStatusRepository.save(readStatus);
        readStatusRepository.save(readStatus2);

        return channelMapper.toChannelPrivateCreateResponseDto(channel);
    }

    @Override
    public List<ChannelResponseDto> findAllByUserId(UUID userId) {
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channelRepository.findAll().stream()
                .filter(channel -> validateUserInChannel(channel,userId)
                                        || channel.getChannelType().equals(ChannelType.PUBLIC))
                .forEach(channel -> {
                    Instant latestTime = findLatestMessage(channel.getMessageIds());
                    channelResponseDtos.add(channelMapper.ofChannelResponseDto(channel, latestTime));
                });
        return channelResponseDtos;
    }

    @Override
    public ChannelResponseDto findByChannelId(UUID channelId) {
        validateActiveChannel(channelId);

        Channel channel = channelRepository.findById(channelId);
        Instant latestTime = findLatestMessage(channel.getMessageIds());
        return channelMapper.ofChannelResponseDto(channel,latestTime);
    }
    @Override
    public ChannelResponseDto updateChannel(UUID channelId, ChannelUpdateDto channelUpdateDto) {
        validateActiveChannel(channelId);
        validatePublicChannel(channelId);

        Channel findChannel = channelRepository.findById(channelId);
        Optional.ofNullable(channelUpdateDto.name()).ifPresent(findChannel::setChannelName);
        Optional.ofNullable(channelUpdateDto.description()).ifPresent(findChannel::setDescription);
        findChannel.setUpdatedAt(Instant.now());

        channelRepository.save(findChannel);
        Instant latestMessage = findLatestMessage(findChannel.getMessageIds());
        return channelMapper.ofChannelResponseDto(findChannel, latestMessage);
    }

    @Override
    public void deleteChannel(UUID id) {
        validateActiveChannel(id);
        Channel channel = channelRepository.findById(id);
        /***********************************
         * 전체 메시지 중 해당 채널의 메시지 삭제
         ***********************************/
        channel.getMessageIds().stream()
                .forEach(messageId -> {
                    System.out.println(channel);
                    removeMessage(channel,messageId);
                });// 채널 내 모든 메세지 삭제
        channel.clearMessages();
        channel.setActive(ActiveStatus.DELETE);
        channelRepository.delete(channel);  // 전체 채널 리스트에서 해당 채널 삭제
        readStatusRepository.delete(channel.getId());
    }

    private void removeMessage(Channel channel, UUID messageId) {
        validateActiveChannel(channel.getId());
        if (!channel.getMessageIds().contains(messageId)) throw new IllegalStateException(messageId + " Message does not exist");
//        {
//            System.out.println(messageId + " Message does not exist");
//            return;
//        }

        Message message = messageRepository.findById(messageId);
        message.setActive(ActiveStatus.DELETE);
        message.setUpdatedAt(Instant.now());
        messageRepository.delete(message);
    }

    public void addUser(UUID channelId, UUID userId) {
        Channel channel = channelRepository.findById(channelId);
        channel.addUser(userId);
        ReadStatus readStatus = new ReadStatus(channelId,userId,Instant.now());
        readStatusRepository.save(readStatus);
    }

    // 테스트용 내용확인

    @Override
    public List<ChannelResponseDto> findAllChannels() {
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channelRepository.findAll().stream()
                .forEach(channel -> {
                    Instant  latestTime = findLatestMessage(channel.getMessageIds());
                    channelResponseDtos.add(channelMapper.ofChannelResponseDto(channel,latestTime));
                });
        return channelResponseDtos;
    }
    private void validateActiveChannel(UUID id) {
        if (!channelRepository.findById(id).getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalStateException("Channel is not active");
    }

    private void validatePublicChannel(UUID id) {
        if(channelRepository.findById(id).getChannelType().equals(ChannelType.PRIVATE)) throw new IllegalStateException("Cannot modify a private channel.");
    }

    private Instant findLatestMessage(List<UUID> messageIds) {
        List<Message> messages = messageRepository.findAll().stream()
                .filter(message -> messageIds.contains(message.getId()))
                .toList();
        Optional<Message> latestMessage = messages.stream()
                .max(Comparator.comparing(Message::getUpdatedAt));
        if(latestMessage.isEmpty()) return Instant.MIN;
        return latestMessage.get().getUpdatedAt();
    }

    private boolean validateUserInChannel(Channel channel, UUID userId) {
        if (channel.getHostUserId() != null && channel.getHostUserId().equals(userId)) {
            return true;
        }
        if (channel.getRecipientId() != null && channel.getRecipientId().equals(userId)) {
            return true;
        }

        return false;
    }

    private void validateUser(UUID userId) {
        userRepository.findById(userId);
    }
}
