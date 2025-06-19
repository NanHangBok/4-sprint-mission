package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    public List<User> findAll();
    public User findById(UUID id);
    public void delete(User user);
    public void save(User user);
    public User findByName(String name);
}
