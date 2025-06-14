package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository {
    public List<Channel> findAll();
    public Channel findById(UUID id);
    public void delete(Channel channel);
    public void save(Channel channel);
}
