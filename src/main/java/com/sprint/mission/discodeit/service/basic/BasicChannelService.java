package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelPostDto;
import com.sprint.mission.discodeit.dto.ChannelPrivatePostDto;
import com.sprint.mission.discodeit.dto.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelUpdateDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.mapper.ChannelMapper;
import com.sprint.mission.discodeit.mapper.ReadStatusMapper;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.service.ChannelService;
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

    private void validateActiveChannel(UUID id) {
        if (!channelRepository.findById(id).getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalStateException("Channel is not active");
    }

    private void validatePublicChannel(UUID id) {
        if(channelRepository.findById(id).getChannelType().equals(ChannelType.PRIVATE)) throw new IllegalStateException("Cannot modify a private channel.");
    }

    public Instant findLatestMessage(List<UUID> messageIds) {
        List<Message> messages = messageRepository.findAll().stream()
                .filter(message -> messageIds.contains(message.getId()))
                .toList();
        Optional<Message> latestMessage = messages.stream()
                .max(Comparator.comparing(Message::getUpdatedAt));
        if(latestMessage.isEmpty()) return Instant.MIN;
        return latestMessage.get().getUpdatedAt();
    }

    @Override
    public Channel createPublicChannel(ChannelPostDto channelPostDto) {
        Channel channel = channelMapper.toPublicChannel(channelPostDto);
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);

        return channel;
    }

    @Override
    public Channel createPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto) {
        Channel channel = channelMapper.toPrivateChannel(channelPrivatePostDto);
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);

        ReadStatus readStatus = new ReadStatus(channelPrivatePostDto.user1(),channel.getId(),Instant.now());
        ReadStatus readStatus2 = new ReadStatus(channelPrivatePostDto.user2(),channel.getId(),Instant.now());
        readStatusRepository.save(readStatus);
        readStatusRepository.save(readStatus2);

        return channel;
    }

    @Override
    public List<ChannelResponseDto> findAllByUserId(UUID userId) {
        List<ChannelResponseDto> channelResponseDtos = new ArrayList<>();
        channelRepository.findAll().stream()
                .filter(channel -> channel.getUserIds().contains(userId)
                                        || channel.getChannelType().equals(ChannelType.PUBLIC))
                .forEach(channel -> {
                    Instant latestTime = findLatestMessage(channel.getMessageIds());
                    channelResponseDtos.add(channelMapper.toChannelResponseDto(channel, latestTime));
                });
        return channelResponseDtos;
    }

    @Override
    public ChannelResponseDto findByChannelId(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        Instant latestTime = findLatestMessage(channel.getMessageIds());
        return channelMapper.toChannelResponseDto(channel,latestTime);
    }

    @Override
    public void updateChannel(ChannelUpdateDto channelUpdateDto) {
        validateActiveChannel(channelUpdateDto.id());
        validatePublicChannel(channelUpdateDto.id());

        Channel findChannel = channelRepository.findById(channelUpdateDto.id());
        Optional.ofNullable(channelUpdateDto.name()).ifPresent(findChannel::setChannelName);
        Optional.ofNullable(channelUpdateDto.description()).ifPresent(findChannel::setDescription);
        findChannel.setUpdatedAt(Instant.now());

        channelRepository.save(findChannel);
    }

    @Override
    public void deleteChannel(UUID id) {
        validateActiveChannel(id);
        Channel channel = channelRepository.findById(id);
        /***********************************
         * 전체 메시지 중 해당 채널의 메시지 삭제
         ***********************************/
        channel.getMessageIds().stream()
                .forEach(message -> {
                    removeMessage(channel,message);
                });// 채널 내 모든 메세지 삭제
        channel.setActive(ActiveStatus.DELETE);
        channelRepository.delete(channel);  // 전체 채널 리스트에서 해당 채널 삭제
        readStatusRepository.delete(channel.getId());
    }

    // 메세지에 내부 정보 삭제
    @Override
    public void removeMessage(Channel channel, UUID messageId) {
        validateActiveChannel(channel.getId());
        if (!channel.getMessageIds().contains(messageId)) throw new IllegalStateException("Message does not exist");

        Message message = messageRepository.findById(messageId);
        channel.removeMessageFromChannel(message);
        channelRepository.save(channel);
        message.setActive(ActiveStatus.DELETE);
        message.setUpdatedAt(Instant.now());
        messageRepository.delete(message);
    }

    // 테스트용 내용확인
    @Override
    public List<Channel> findAllChannels() {
        return channelRepository.findAll();
    }
}
