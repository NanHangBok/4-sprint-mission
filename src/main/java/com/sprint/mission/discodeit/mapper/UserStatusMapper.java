package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.UserStatusPostDto;
import com.sprint.mission.discodeit.dto.UserStatusUpdateDto;
import com.sprint.mission.discodeit.entity.UserStatus;

public class UserStatusMapper {
    public UserStatus toUserStatus(UserStatusPostDto postDto) {
        return new UserStatus(postDto.userId(),postDto.latestActiveAt());
    }
}
