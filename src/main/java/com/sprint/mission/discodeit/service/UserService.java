package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserCreateResponseDto;
import com.sprint.mission.discodeit.dto.UserPostDto;
import com.sprint.mission.discodeit.dto.UserResponseDto;
import com.sprint.mission.discodeit.dto.UserUpdateDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserCreateResponseDto createUser(UserPostDto profileUserDto);
    public List<UserResponseDto> findAllUsers();
    public UserResponseDto findUserById(UUID userId);
    public UserResponseDto getUserByName(String name);
    public UserResponseDto updateUser(UserUpdateDto userUpdateDto);
    public void deleteUser(UUID userId);
}
