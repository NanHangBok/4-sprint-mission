package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.CreateUserStatusDto;
import com.sprint.mission.discodeit.dto.UpdateUserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(CreateUserStatusDto createUserStatusDto);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    void update(UpdateUserStatusDto updateUserStatusDto);
    void updateByUserId(UUID userId, Instant latestActiveAt);
    void delete(UUID id);
}
