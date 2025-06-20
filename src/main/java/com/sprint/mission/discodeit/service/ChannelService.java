package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelPostDto;
import com.sprint.mission.discodeit.dto.ChannelPrivatePostDto;
import com.sprint.mission.discodeit.dto.ChannelResponseDto;
import com.sprint.mission.discodeit.dto.ChannelUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    public Channel createPublicChannel(ChannelPostDto channelPostDto);
    public Channel createPrivateChannel(ChannelPrivatePostDto channelPrivatePostDto);
    public List<ChannelResponseDto> findAllByUserId(UUID userId);
    public ChannelResponseDto find(UUID channelId);
    public void updateChannel(ChannelUpdateDto channelUpdateDto);
    public void deleteChannel(UUID channelId);
    public void removeUserFromChannel(Channel channel, UUID userId);
    public void addUserToChannel(Channel channel, User user);
    public void removeMessage(Channel channel, UUID messageId);

    public List<Channel> findAllChannels();
}
