package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public User toUserForCreate (UserPostDto userPostDto, UUID profileId) {
        return new User(userPostDto.name(), userPostDto.password(), userPostDto.email(), profileId);
    }

    public UserResponseDto toUserResponseDto(User user, UserStatus userStatus) {
        return new UserResponseDto(user.getName(), user.getEmail(), user.getProfileId(), userStatus);
    }

    public User toUserForUpdate(UserUpdateDto userUpdateDto, UUID profileId) {
        return new User(userUpdateDto.name(),userUpdateDto.password(),userUpdateDto.presenceStatus(),profileId);
    }
}
