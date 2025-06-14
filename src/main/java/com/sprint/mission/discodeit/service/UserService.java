package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    public User createUser(String name, String password, String email);
    public List<User> getUsers();
    public User getUsersById(UUID userId);
    public void updateUser(User user, User updateUser);
    public void deleteUser(User User);
    public void leaveChannel(User user, Channel channel);
    public void removeMessage(User user, Message message);
    public boolean isDuplicateEmail(String email);
}
