package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChannelMapper {
    public Channel toPublicChannel(ChannelPostDto channelPostDto) {
        return new Channel(channelPostDto.hostUserId(),channelPostDto.channelName(),channelPostDto.description());
    }
    public Channel toPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto) {
        return new Channel(channelPrivatePostDto.host(),channelPrivatePostDto.recipient());
    }
    public ChannelResponseDto ofChannelResponseDto(Channel channel, Instant latestMessageTime) {
        return new ChannelResponseDto(channel.getId(), channel.getHostUserId(), channel.getRecipientId(), channel.getChannelName(), channel.getDescription(), channel.getChannelType(), channel.getUserIds(), latestMessageTime);
    }

    public ChannelPublicCreateResponseDto toChannelPublicCreateResponseDto(Channel channel) {
        return new ChannelPublicCreateResponseDto(channel.getId(),channel.getChannelType(),channel.getChannelName(),channel.getDescription());
    }

    public ChannelPrivateCreateResponseDto toChannelPrivateCreateResponseDto(Channel channel) {
        return new ChannelPrivateCreateResponseDto(channel.getId(), channel.getChannelType(), channel.getHostUserId(), channel.getRecipientId());
    }
}
