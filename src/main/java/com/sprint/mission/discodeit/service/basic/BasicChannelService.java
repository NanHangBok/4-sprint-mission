package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.ReadChannelDto;
import com.sprint.mission.discodeit.dto.UpdateChannelDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
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

    public void validateActiveChannel(UUID id) {
        if (!channelRepository.findById(id).getActive().equals(ActiveStatus.ACTIVE)) throw new IllegalStateException("Channel is not active");
    }

    private void validatePublicChannel(UUID id) {
        if(channelRepository.findById(id).getChannelType().equals(ChannelType.PRIVATE)) throw new IllegalStateException("Cannot modify a private channel.");
    }
    public Instant findLatestMessage(List<Message> messages) {
        Optional<Message> latestMessage = messages.stream()
                .max(Comparator.comparing(Message::getUpdatedAt));
        if(latestMessage.isEmpty()) return null;

        return latestMessage.get().getUpdatedAt();
    }
    @Override
    public Channel createPublicChannel(CreateChannelDto createChannelDto) {
        Channel channel = new Channel(createChannelDto.hostUserId(), createChannelDto.channelName(), createChannelDto.description());
        // if (!(user.getActive().equals(ActiveStatus.ACTIVE))) throw new IllegalStateException("User is not active");
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);

        return channel;
    }

    @Override
    public Channel createPrivateChannel(CreateChannelDto createChannelDto) {
        Channel channel = new Channel(createChannelDto.hostUserId());
        channel.setActive(ActiveStatus.ACTIVE);
        channelRepository.save(channel);

        ReadStatus readStatus = new ReadStatus(createChannelDto.hostUserId(),channel.getId(),Instant.now());
        readStatusRepository.save(readStatus);

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
    public List<ReadChannelDto> findAllByUserId(UUID userId) {
        List<ReadChannelDto> readChannelDtos = new ArrayList<>();
        channelRepository.findAll().stream()
                .filter(channel -> channel.getUsers().contains(userId)
                                        || channel.getChannelType().equals(ChannelType.PUBLIC))
                .forEach(channel -> {
                    Instant latestTime = findLatestMessage(channel.getMessages());
                    if (channel.getChannelType().equals(ChannelType.PRIVATE)) {
                        readChannelDtos.add(new ReadChannelDto(channel.getHostUserId(),channel.getId(), channel.getChannelType(), channel.getUserIds(), latestTime));
                    } else {
                        readChannelDtos.add(new ReadChannelDto(channel.getHostUserId(), channel.getId(), channel.getChannelType(), null, latestTime));
                    }
                });
        return readChannelDtos;
    }

    @Override
    public ReadChannelDto find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        Instant latestTime = findLatestMessage(channel.getMessages());
        if (channel.getChannelType().equals(ChannelType.PRIVATE)) {
            return new ReadChannelDto(channel.getHostUserId(),channel.getId(), channel.getChannelType(), channel.getUserIds(), latestTime);
        }
        return new ReadChannelDto(channel.getHostUserId(), channel.getId(), channel.getChannelType(), null, latestTime);
    }

    @Override
    public void updateChannel(UpdateChannelDto updateChannelDto) {
        validateActiveChannel(updateChannelDto.id());
        validatePublicChannel(updateChannelDto.id());

        Channel channel = channelRepository.findById(updateChannelDto.id());
        boolean isUpdated = false;

        if (updateChannelDto.name() != null) {
            channel.setChannelName(updateChannelDto.name());
            isUpdated = true;
        }
        if (updateChannelDto.description() != null) {
            channel.setDescription(updateChannelDto.description());
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
        channel.getMessages().stream()
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
        User user = channel.getUsers().stream()
                .filter(u -> u.getId().equals(userId))
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
    public void removeMessage(Channel channel, Message message) {
        validateActiveChannel(channel.getId());
        if (!channel.getMessages().contains(message)) throw new IllegalStateException("Message does not exist");

        channel.removeMessage(message);

        messageRepository.delete(message);
        channelRepository.save(channel);
        message.setActive(ActiveStatus.DELETE);
        message.setUpdatedAt(Instant.now());
        messageRepository.delete(message);
    }
}
