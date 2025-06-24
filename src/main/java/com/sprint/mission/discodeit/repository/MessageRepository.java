package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    public List<Message> findAll();
    public List<Message> findAllActive();
    public Message findById(UUID id);
    public void delete(Message message);
    public void save(Message message);
}
