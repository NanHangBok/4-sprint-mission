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
    private final ReadStatusRepository readStatusRepository;
    private final MessageRepository messageRepository;
    private final ChannelMapper channelMapper;
    private final ReadStatusMapper readStatusMapper;

    public void validateActiveChannel(UUID id) {
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
        Channel channel = channelMapper.toChannel(channelPostDto);
        // if (!(user.getActive().equals(ActiveStatus.ACTIVE))) throw new IllegalStateException("User is not active");
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



//    @Override
//    public List<ReadChannelDto> findAllByUserId() {
//        List<ReadChannelDto> readChannelDtos = new ArrayList<>();
//        channelRepository.findAll().stream()
//                .forEach(channel -> {
//                    Instant lastestTime = findLatestMessage(channel.getMessages());
//                    readChannelDtos.add(new ReadChannelDto(channel.getHostUserId(),channel.getId(),channel.getUserIds(),channel.getChannelType(),lastestTime));
//                });
//
//        return readChannelDtos;
//    }

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
    public ChannelResponseDto find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        Instant latestTime = findLatestMessage(channel.getMessageIds());
        return channelMapper.toChannelResponseDto(channel,latestTime);
    }

    @Override
    public void updateChannel(ChannelUpdateDto channelUpdateDto) {
        validateActiveChannel(channelUpdateDto.id());
        validatePublicChannel(channelUpdateDto.id());

        Channel channel = channelRepository.findById(channelUpdateDto.id());
        boolean isUpdated = false;

        if (channelUpdateDto.name() != null) {
            channel.setChannelName(channelUpdateDto.name());
            isUpdated = true;
        }
        if (channelUpdateDto.description() != null) {
            channel.setDescription(channelUpdateDto.description());
            isUpdated = true;
        }

        if (isUpdated) {
            channel.setUpdatedAt(Instant.now());
            channelRepository.save(channel);
        }
    }

    @Override
    public void deleteChannel(UUID id) {
        validateActiveChannel(id);
        Channel channel = channelRepository.findById(id);

        channel.setActive(ActiveStatus.DELETE);
        channelRepository.delete(channel);  // 전체 채널 리스트에서 해당 채널 삭제
        /***********************************
         * 전체 메시지 중 해당 채널의 메시지 삭제
         ***********************************/
        channel.getMessageIds().stream()
                .forEach(message -> {
                    removeMessage(channel,message);
                });// 채널 내 모든 메세지 삭제
        readStatusRepository.delete(channel.getId());
    }

//        /***********************************
//         * 채널을 가지고 있는 유저에게 채널 삭제
//         ***********************************/
//        channel.getUsers().stream()
//                .forEach(user -> {
//                    user.getChannels().remove(channel);
//                    user.setUpdatedAt(Instant.now());
//                    userRepository.save(user);
//                });
//        // 모든 유저에게 해당 채널 삭제
//
    // 채널 삭제

    /***********************************
     * 해당 채널의 존재하는 특정 유저를 id를 통해 삭제
     * @param channel 삭제할 유저가 있는 채널
     * @param userId 삭제할 유저의 id
     ***********************************/
    @Override
    public void removeUserFromChannel(Channel channel, UUID userId) {
        UUID uId = channel.getUserIds().stream()
                .filter(u -> u.equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("The user does not belong to the channel"));

//        user.removeChannel(channel);  // 유저에서 해당 채널을 삭제하고
        channel.setUpdatedAt(Instant.now());  // 채널의 업데이트 시간 수정
        channelRepository.save(channel);
//        userRepository.save(user);
    }

    /***********************************
     * 해당 채널의 새로운 유저 추가
     * @param channel 유저를 추가할 채널
     * @param user 채널에 추가할 유저
     ***********************************/
    @Override
    public void addUserToChannel(Channel channel, User user) {
        validateActiveChannel(channel.getId());
        if (!user.getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalStateException("User is not active");

//        channel.addUser(user);  // 해당 유저에 채널을 추가하고
        channel.setUpdatedAt(Instant.now());  // 채널의 업데이트 시간 수정
        channelRepository.save(channel);
//        userRepository.save(user);
    }

    // 메세지에 내부 정보 삭제
    @Override
    public void removeMessage(Channel channel, UUID messageId) {
        validateActiveChannel(channel.getId());
        if (!channel.getMessageIds().contains(messageId)) throw new IllegalStateException("Message does not exist");

        channel.removeMessage(messageId);
        Message message = messageRepository.findById(messageId);
        messageRepository.delete(message);
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
