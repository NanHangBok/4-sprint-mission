package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public User addUser(String name, String password, String email);
    public List<User> getUsers();
    public Optional<User> getUsersById(UUID userId);
    public void updateUser(UUID userId, int select, String updatedText);
    public void deleteUser(UUID userId);
}
