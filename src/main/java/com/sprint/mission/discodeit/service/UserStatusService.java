package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusPostDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateDto;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatus create(UserStatusPostDto userStatusPostDto);
    UserStatus find(UUID id);
    List<UserStatus> findAll();
    void update(UserStatusUpdateDto userStatusUpdateDto);
    void updateByUserId(UUID userId, Instant latestActiveAt);
    void delete(UUID id);
}
