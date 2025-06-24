package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
//@Profile("jcf")
//@Primary
//@ConditionalOnProperty(name = "discodeit.repository.type", havingValue = "jcf", matchIfMissing = true)
public class JCFChannelRepository implements ChannelRepository {
    private List<Channel> data = new ArrayList<>();

    @Override
    public List<Channel> findAll() {
        return data;
    }

    @Override
    public Channel findById(UUID id) {
        Channel channel = data.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));
        return channel;
    }

    @Override
    public void save(Channel channel) {
        if (data.contains(channel)) {
            data.stream()
                    .map(c -> c.equals(channel) ? channel : c)
                    .forEach(c -> {});
        } else {
            data.add(channel);
        }
    }

    @Override
    public void delete(Channel channel) {
        data.remove(channel);
    }
}
