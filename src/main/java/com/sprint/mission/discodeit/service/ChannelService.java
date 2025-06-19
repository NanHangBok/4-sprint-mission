package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.CreateChannelDto;
import com.sprint.mission.discodeit.dto.ReadChannelDto;
import com.sprint.mission.discodeit.dto.UpdateChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    public Channel createPublicChannel(CreateChannelDto createChannelDto);
    public Channel createPrivateChannel(CreateChannelDto createChannelDto);
    public List<ReadChannelDto> findAllByUserId(UUID userId);
    public ReadChannelDto find(UUID channelId);
    public void updateChannel(UpdateChannelDto updateChannelDto);
    public void deleteChannel(UUID channelId);
    public void removeUserFromChannel(Channel channel, UUID userId);
    public void addUserToChannel(Channel channel, User user);
    public void removeMessage(Channel channel, Message message);
}
