package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.ChannelPostDto;
import com.sprint.mission.discodeit.dto.ChannelPrivatePostDto;
import com.sprint.mission.discodeit.dto.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChannelMapper {
    public Channel toChannel(ChannelPostDto channelPostDto) {
        return new Channel(channelPostDto.hostUserId(),channelPostDto.channelName(),channelPostDto.description());
    }
    public Channel toPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto) {
        return new Channel(channelPrivatePostDto.user1(),channelPrivatePostDto.user2());
    }
    public ChannelResponseDto toChannelResponseDto(Channel channel, Instant latestMessageTime) {
        return new ChannelResponseDto(channel.getHostUserId(), channel.getChannelName(), channel.getChannelType(), channel.getUserIds(), latestMessageTime);
    }
    public Channel toChannel(ChannelUpdateDto channelUpdateDto) {
        return new Channel(channelUpdateDto.id(), channelUpdateDto.name(), channelUpdateDto.description());
    }
}
