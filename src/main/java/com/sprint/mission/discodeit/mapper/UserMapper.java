package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.*;
import com.sprint.mission.discodeit.entity.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public User toUser(UserPostDto userPostDto, UUID profileId) {
        return new User(userPostDto.name(), userPostDto.nickname(), userPostDto.password(), userPostDto.email(), profileId);
    }

    public UserResponseDto toUserResponseDto(User user, UserStatusResponseDto userStatusResponseDto) {
        return new UserResponseDto(user.getId(), user.getNickname(), user.getEmail(), user.getProfileId(), userStatusResponseDto);
    }

    public User toUserForUpdate(UserUpdateDto userUpdateDto,UUID profileId) {
        return new User(userUpdateDto.nickname(), userUpdateDto.password(), userUpdateDto.presenceStatus(), profileId);
    }

    public UserLoginResponseDto toUserLoginResponseDto(User user) {
        return new UserLoginResponseDto(user.getId(), user.getProfileId(), user.getNickname(), user.getEmail(), user.getPresenceStatus());
    }

    public UserCreateResponseDto toUserCreateResponseDto(User user, UserStatusResponseDto userStatusResponseDto) {
        return new UserCreateResponseDto(user.getId(),user.getName(),user.getNickname(),user.getEmail(),user.getProfileId(),userStatusResponseDto);
    }
}
