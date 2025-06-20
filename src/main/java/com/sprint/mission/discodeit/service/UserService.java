package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserPostDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserUpdateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
//    public User createUser(String name, String password, String email);
    public User createUser(UserPostDto profileUserDto);
    public List<UserResponseDto> getUsers();
    public UserResponseDto getUserById(UUID userId);
    public UserResponseDto getUserByName(String name);
    public void updateUser(UserUpdateDto userUpdateDto);
    public void deleteUser(UUID userId);
    public void leaveChannel(User user, Channel channel);
    public void removeMessage(User user, Message message);
    public boolean isDuplicateEmail(String email);
}
