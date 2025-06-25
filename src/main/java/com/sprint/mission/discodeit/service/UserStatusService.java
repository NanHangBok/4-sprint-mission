package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusPostDto;
import com.sprint.mission.discodeit.dto.UserStatusResponseDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateDto;
import com.sprint.mission.discodeit.entity.UserStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusResponseDto create(UserStatusPostDto userStatusPostDto);
    UserStatusResponseDto find(UUID id);
    List<UserStatusResponseDto> findAll();
    UserStatusResponseDto update(UserStatusUpdateDto userStatusUpdateDto);
    UserStatusResponseDto updateByUserId(UUID userId, Instant latestActiveAt);
    void delete(UUID id);

    UserStatusResponseDto findByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
