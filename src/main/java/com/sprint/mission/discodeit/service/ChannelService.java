package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelService {
    public Channel createChannel(User user, String channelName);
    public List<Channel> getChannels();
    public Optional<Channel> getChannelById(UUID channelId);
    public void updateChannel(UUID channelId, int select, String updatedText);
    public void deleteChannel(Channel channel);
    public void deleteChannelUser(Channel channel, UUID userId);
    public void deleteChannelUser(Channel channel, User user);
    public void addChannelUser(Channel channel, User user);
}
