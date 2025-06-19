package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.UserStatus;

import java.util.List;
import java.util.UUID;

public interface UserStatusRepository {
    void delete(UUID id);

    UserStatus findById(UUID userId);

    void save(UserStatus userStatus);

    List<UserStatus> findAll();
}
