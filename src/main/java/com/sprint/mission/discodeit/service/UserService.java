package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(UserCreateRequest userCreateRequest, UUID binaryContentId);

    User updateUser(UUID userId, UserUpdateRequest userUpdateRequest, UUID binaryContentId);

    void deleteUser(UUID userId);

    List<User> findAll();
}
