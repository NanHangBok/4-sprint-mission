package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public UserCreateResponseDto createUser(UserCreateDto userCreateDto);
    public List<UserResponseDto> findAllUsers();
    public UserResponseDto findUserById(UUID userId);
    public UserResponseDto getUserByName(String name);
    public UserResponseDto updateUser(UUID userId, UserUpdateDto userUpdateDto);
    public void deleteUser(UUID userId);
    List<UserDto> findAll();
}
